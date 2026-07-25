package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ScrollableTabRow
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.ui.basic.FlowPillButton
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow
import com.averyvi.spiritfire.ui.components.BigLogDisplayCard

@Composable
fun HabitOverview(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository
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
    val displayedHabits = OverviewViewModel.displayedHabits.collectAsState().value
    val filtredLogs = OverviewViewModel.filtredLogs.collectAsState().value

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        val showDays = remember { mutableStateOf(periodsToShow.MONTH) }

        Column() {
            val scrollState = rememberScrollState()
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.horizontalScroll(
                    state = scrollState,
                )
            ) {
                periodsToShow.entries.forEachIndexed { index, pair ->
                    FlowPillButton(
                        onClick = {
                            showDays.value = pair
                        }
                    ) {
                        val isPlural = pair.amount > 1
                        Text(
                            text = pair.amount.toString() + if(isPlural) {
                                stringResource(pair.type.string2Plural)
                            } else {
                                stringResource(pair.type.string2Singular)
                            }
                        )
                    }
                }
            }

            /*
            HabitSortingFilteringChipsRow(
                sortingFiltering = habitFilterViewModel.sortingFiltering.collectAsState().value,
            )*/
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(displayedHabits.size) { viewPosition ->
                val habitRow = displayedHabits[viewPosition]
                val logsList = filtredLogs.filter { it.habit == habitRow.id }

                BigLogDisplayCard(
                    habitRow = habitRow,
                    logsList = logsList,
                    periodsToShow = showDays.value
                )
            }
        }
        Text("bottom")
    }
}

/**
 * Completions screen
 * // completed today + streaks(card)
 * // this month (group of cards)
 * // completion ratio this year
 */