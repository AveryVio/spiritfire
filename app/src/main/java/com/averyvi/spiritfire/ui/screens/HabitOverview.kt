package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.definitions.ui.OverviewViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.ui.appUI.top.GeneralAppBar
import com.averyvi.spiritfire.ui.basic.SmallPill
import com.averyvi.spiritfire.ui.basic.WidthFlexibleChip
import com.averyvi.spiritfire.ui.basic.periodsToShow
import com.averyvi.spiritfire.ui.components.BigLogDisplayCard
import com.averyvi.spiritfire.ui.components.OldBigLogDisplayCard
import com.averyvi.spiritfire.ui.components.UICard

@Composable
fun HabitOverview(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository,
    outerPadding: PaddingValues
) {
    val OverviewVMfactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OverviewViewModel(
                habitRepository = habitRepository,
                habitFilterViewModel = habitFilterViewModel,
                logSortingFiltering = LogSortingFiltering.TESTING
            ) as T
        }
    }
    val OverviewViewModel: OverviewViewModel = viewModel(factory = OverviewVMfactory)
    val cardUIStates = OverviewViewModel.overviewUiState.collectAsState().value
    val displayedHabits = OverviewViewModel.displayedHabits.collectAsState().value
    val filtredLogs = OverviewViewModel.filtredLogs.collectAsState().value


    Scaffold(
        modifier = Modifier.padding(outerPadding),
        topBar = {
            GeneralAppBar()
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(8.dp).padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val showDays = OverviewViewModel.showDays.collectAsState().value

            val scrollState = rememberScrollState()



            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.horizontalScroll(
                    state = scrollState,
                )
            ) {
                periodsToShow.entries.forEach { pair ->
                    SmallPill(
                        onClick = {
                            OverviewViewModel.changeShownDays(pair)
                        }
                    ) {
                        val isPlural = pair.amount > 1
                        Text(
                            text = pair.amount.toString() + if (isPlural) {
                                stringResource(pair.type.string2Plural)
                            } else {
                                stringResource(pair.type.string2Singular)
                            }
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = cardUIStates.size,
                    key = { index -> cardUIStates[index].habitRow.id }
                ) { viewPosition ->
                    val habitRow = cardUIStates[viewPosition].habitRow
                    val logsList = remember(filtredLogs, habitRow.id) {
                        filtredLogs.filter { it.habit == habitRow.id }
                    }

                    BigLogDisplayCard(
                        habitCardUiState = cardUIStates[viewPosition]
                    )/*
                    OldBigLogDisplayCard(
                        habitRow = habitRow,
                        logsList = logsList,
                        periodsToShow = showDays.value
                    )*/
                }
            }
            Text("bottom")
        }
    }
}

/**
 * Completions screen
 * // completed today + streaks(card)
 * // this month (group of cards)
 * // completion ratio this year
 */