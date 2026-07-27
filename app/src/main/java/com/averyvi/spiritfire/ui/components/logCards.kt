package com.averyvi.spiritfire.ui.components

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.transformations.determineGrace
import com.averyvi.spiritfire.data.transformations.getCompletedPeriods
import com.averyvi.spiritfire.ui.basic.SquareChip
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.basic.ShowColumnsOfItems
import com.averyvi.spiritfire.ui.basic.ShowRowsOfItems
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days

@Composable
fun BigLogDisplayCard(
    habitRow: HabitRow,
    logsList: List<HabitLogItem>,
    periodsToShow: periodsToShow,
) {
    UICard() {
        val beginingOfPeriods = 0
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val shownItems = periodsToShow.amount * when(periodsToShow.type) {
            logDisplayLength.DAYS -> 1
            logDisplayLength.WEEKS -> 7
            logDisplayLength.MONTHS -> {
                now.month.length(now.toLocalDate().isLeapYear)
            }
            logDisplayLength.YEARS -> { // todo fix
                Calendar.YEAR.days.inWholeDays.toInt()
            }
        }

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
                        stringResource(habitRow.resetType.descriptorString),
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
                itemsInRow = when(periodsToShow.type) {
                    logDisplayLength.DAYS -> 7
                    logDisplayLength.WEEKS -> 7
                    logDisplayLength.MONTHS -> 7
                    logDisplayLength.YEARS -> 14
                },
                item = { index ->
                    if (isDone[index]) {
                        SquareChip(
                            color = FColour.Red.color,
                            size = 16.dp
                        )
                    } else {
                        val isWithinGrace = determineGrace(
                            completionArrayIndex = index,
                            completionArray = isDone,
                            skipGrace = habitRow.checksSkipGrace,
                        )

                        if (isWithinGrace) {
                            SquareChip(
                                color = FColour.Purple.color,
                                size = 16.dp
                            )
                        } else {
                            SquareChip(
                                color = FColour.Blue.color,
                                size = 16.dp
                            )
                        }
                    }
                },
            )

        }
    }
}

@Composable
fun TransparentLogDisplayBlock(
    habitRow: HabitRow,
    logsList: List<HabitLogItem>,
    periodsToShow: periodsToShow,
) {
    val beginingOfPeriods = 0
    val now = ZonedDateTime.now(ZoneId.systemDefault())
    val shownItems = periodsToShow.amount * when (periodsToShow.type) {
        logDisplayLength.DAYS -> 1
        logDisplayLength.WEEKS -> 7
        logDisplayLength.MONTHS -> {
            now.month.length(now.toLocalDate().isLeapYear)
        }

        logDisplayLength.YEARS -> { // todo fix
            if (now.toLocalDate().isLeapYear) 366 else 365
        }
    }

    val isDone: MutableList<Boolean> = getCompletedPeriods(
        beginingOfPeriods = beginingOfPeriods,
        shownItems = shownItems,
        habitRow = habitRow,
        logsList = logsList,
    )

    val graceGroup = mutableListOf<Boolean>()
    isDone.forEachIndexed { index, bool ->
        if(bool) {
            graceGroup.add(false)
        }
        else {
            graceGroup.add(determineGrace(
                completionArrayIndex = index,
                completionArray = isDone,
                skipGrace = habitRow.checksSkipGrace,
            ))
        }
    }

    val combinedPeriods = when(periodsToShow.type) { // todo make the logic configurable
        logDisplayLength.MONTHS -> { // combine into weeks
            val isDoneContracted = mutableListOf<Boolean>()
            var index = 0
            while (index < isDone.size) {
                var group = 0
                val maxPos = minOf(index + 7, isDone.size)
                for (logPos in index until maxPos) {
                    if (isDone[logPos]) group += 2
                    else if (graceGroup[logPos]) group++
                } // max value = 14 ; acceptable = 9
                isDoneContracted.add(group >= 9)
                index += 7 // Increment to advance loop
            }
            isDoneContracted
        }
        logDisplayLength.YEARS -> { // combine into weeks
            val isDoneContracted = mutableListOf<Boolean>()
            var index = 0
            while (index < isDone.size) {
                var group = 0
                val maxPos = minOf(index + 7, isDone.size)
                for (logPos in index until maxPos) {
                    if (isDone[logPos]) group += 2
                    else if (graceGroup[logPos]) group++
                } // max value = 104 ; acceptable = 66
                isDoneContracted.add(group >= 66)
                index += 7 // Increment to advance loop
            }
            isDoneContracted
        }
        else -> {
            isDone
        }
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val chipSize = when(periodsToShow.type) {
            logDisplayLength.MONTHS -> 20.dp
            logDisplayLength.YEARS -> 24.dp
            else -> 16.dp
        }

        Text(
            text = (
                    if (logsList.isNotEmpty()) logsList[0].checks.toString() else "0"
                    ) + " / " + habitRow.checksAmount.toString()
        )

        ShowColumnsOfItems(
            itemsCount = combinedPeriods.size,
            itemsInRow = when (periodsToShow.type) {
                logDisplayLength.MONTHS -> 18
                logDisplayLength.YEARS -> 14
                else -> 14
            },
            item = { index ->
                if (combinedPeriods[index]) {
                    SquareChip(
                        color = FColour.Red.color,
                        size = chipSize
                    )
                } else {
                    val isWithinGrace = determineGrace(
                        completionArrayIndex = index,
                        completionArray = combinedPeriods,
                        skipGrace = habitRow.checksSkipGrace,
                    )

                    if (isWithinGrace) {
                        SquareChip(
                            color = FColour.Purple.color,
                            size = chipSize
                        )
                    } else {
                        SquareChip(
                            color = FColour.Blue.color,
                            size = chipSize
                        )
                    }
                }
            },
        )
    }
}