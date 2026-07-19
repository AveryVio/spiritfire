package com.averyvi.spiritfire.ui.screens

import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.ColumnType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.FilteringType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering.Companion.addFilter
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering.Companion.addSorting
import com.averyvi.spiritfire.data.transformations.isWithinPeriod
import com.averyvi.spiritfire.ui.basic.FlowPillButton
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.components.UICard
import kotlinx.coroutines.flow.Flow
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoField
import java.time.temporal.TemporalField
import java.util.Collections.emptyList
import kotlin.compareTo
import kotlin.math.ceil

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
                sortingFiltering = SortingFiltering.TESTING // todo: temp
            ) as T
        }
    }
    val OverviewViewModel: OverviewViewModel = viewModel(factory = OverviewVMfactory)
    val displayedHabits = OverviewViewModel.displayedHabits.collectAsState().value
    val filtredLogs = OverviewViewModel.filtredLogs.collectAsState().value //todo add sorting filtering for logs
    val sortingFiltering = OverviewViewModel.sortingFiltering

    Column() {
        FlowRow() {
            FlowPillButton(
            ) {

            }
        }
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
                    modifier = Modifier.padding(4.dp)
                ) {

                    Row() {
                        HabitCheckIcon(
                            icon = habitRow.icon,
                            colour = habitRow.colour,
                            complete = isDone[0],
                            onClick = {},
                            size = 32.dp + 16.dp + 8.dp
                        )
                        Column() {
                            Text(
                                habitRow.name
                            )
                            Text(
                                stringResource(habitRow.resetType.uiText)
                            )
                        }
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
                                        color = FColour.Red.color,
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
}

@Composable
fun ShowRowsOfItems(
    itemsCount: Int,
    itemsInRow: Int,
    item: @Composable (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        var itemsRemaining = 0
        while ( itemsRemaining < itemsCount ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (row in (0..<itemsInRow)) {
                    if (itemsRemaining < itemsCount) {
                        item(itemsRemaining)
                        itemsRemaining++
                    } else { break }
                }
            }
        }
    }
}

@Composable
fun CompletionChip(
    color: Color = FColour.Grey.color,
    size: Dp = 32.dp
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        ),
        modifier = Modifier.size(size)
    ) { }
}

/**
 * Completions screen
 * // completed today + streaks(card)
 * // this month (group of cards)
 * // completion ratio this year
 */

fun getCompletedPeriods(
    beginingOfPeriods: Int,
    shownItems: Int,
    habitRow: HabitRow,
    logsList: List<HabitLogItem>
): MutableList<Boolean> {
    var completedPeriods: MutableList<Boolean> = mutableListOf()

    for (period in (beginingOfPeriods)..<(beginingOfPeriods + shownItems)) {
        val logsInPeriod = logsList.filter { log ->
            isWithinPeriod(
                habitRow = habitRow,
                targetTimestamp = log.logTime,
                periodsAgo = period.toLong(),
            )
        }
        val totalChecks = logsInPeriod.sumOf { it.checks }

        val requiredChecks = if (habitRow.checksAmount > 0) habitRow.checksAmount else 1

        completedPeriods.add(period, totalChecks >= requiredChecks)
    }

    return completedPeriods
}

fun determineGrace(
    completionArrayIndex: Int,
    completionArray: MutableList<Boolean>,
    skipGrace: Int,
): Boolean {

    var misses = 1

    /*var newerPeriods = completionArrayIndex - 1
    while (newerPeriods >= 0 && !isDone[newerPeriods]) {
        misses++
        newerPeriods--
    }*/

    var olderPeriods = completionArrayIndex + 1
    while (olderPeriods < completionArray.size && !completionArray[olderPeriods]) {
        misses++
        olderPeriods++
    }

    return misses <= skipGrace
}