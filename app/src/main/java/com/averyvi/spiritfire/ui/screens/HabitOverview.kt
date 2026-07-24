package com.averyvi.spiritfire.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.SColour
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitColumnType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitFilteringType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogColumnType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogFilteringType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering.Companion.addFilter
import com.averyvi.spiritfire.data.transformations.determineGrace
import com.averyvi.spiritfire.data.transformations.getCompletedPeriods
import com.averyvi.spiritfire.data.transformations.isWithinPeriod
import com.averyvi.spiritfire.ui.basic.CompletionChip
import com.averyvi.spiritfire.ui.basic.FlowPillButton
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.basic.HabitSortingFilteringChipsRow
import com.averyvi.spiritfire.ui.basic.IconPillWithValue
import com.averyvi.spiritfire.ui.basic.ShowRowsOfItems
import com.averyvi.spiritfire.ui.components.UICard

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

        Column() {
            HabitSortingFilteringChipsRow(
                sortingFiltering = habitFilterViewModel.sortingFiltering.collectAsState().value,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(displayedHabits.size) { viewPosition ->
                val habitRow = displayedHabits[viewPosition]
                val logsList = filtredLogs.filter { it.habit == habitRow.id }

                UICard() {
                    val beginingOfPeriods = 0
                    val shownItems = 7

                    val isDone: MutableList<Boolean> = getCompletedPeriods(
                        beginingOfPeriods = beginingOfPeriods,
                        shownItems = shownItems,
                        habitRow = habitRow,
                        logsList = logsList,
                    )

                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp + 2.dp)
                        ) {
                            HabitCheckIcon(
                                icon = habitRow.icon,
                                colour = habitRow.colour,
                                complete = isDone[0],
                                onClick = {},
                                size = 32.dp + 16.dp + 8.dp
                            )
                            Column() {
                                Text(
                                    text = habitRow.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    stringResource(habitRow.resetType.uiText),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = (
                                        if (logsList.isNotEmpty()) logsList[0].checks.toString() else "0"
                                        ) + " / " + habitRow.checksAmount.toString()
                            )
                        }

                        ShowRowsOfItems(
                            itemsCount = isDone.size,
                            itemsInRow = 7,
                            item = { index ->
                                if (isDone[index]) {
                                    CompletionChip(
                                        color = FColour.Red.color,
                                        size = 32.dp
                                    )
                                } else {
                                    val isWithinGrace = determineGrace(
                                        completionArrayIndex = index,
                                        completionArray = isDone,
                                        skipGrace = habitRow.checksSkipGrace,
                                    )

                                    if (isWithinGrace) {
                                        CompletionChip(
                                            color = FColour.Purple.color,
                                            size = 32.dp
                                        )
                                    } else {
                                        CompletionChip(
                                            color = FColour.Blue.color,
                                            size = 32.dp
                                        )
                                    }
                                }
                            },
                        )

                    }
                }
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