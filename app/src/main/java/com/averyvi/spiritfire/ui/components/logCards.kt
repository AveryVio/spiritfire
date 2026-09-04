package com.averyvi.spiritfire.ui.components

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.ui.ChartData
import com.averyvi.spiritfire.data.transformations.determineGrace
import com.averyvi.spiritfire.data.transformations.getCompletedPeriods
import com.averyvi.spiritfire.data.transformations.isWithinPeriod
import com.averyvi.spiritfire.ui.basic.CompletionStreakUI
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.basic.ShowRowsOfItems
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days

@Composable
fun BigLogDisplayCard(
    habitRow: HabitRow,
    logsList: List<HabitLogItem>,
    periodsToShow: periodsToShow,
) {




    /* todo
    something is making this function incredibly laggy and simple paralelism does not work, its awful

    this needs priority fixing otherwise the screen is borderline unusable

    my flagship phone cannot handle it without stuttering, not to mention the 6 yo testing phone

    this is awful
     */




















    val beginingOfPeriods = 0
    val now = ZonedDateTime.now(ZoneId.systemDefault())

    var isDone by remember { mutableStateOf<List<Boolean>>(emptyList()) }
    var withinGrace by remember { mutableStateOf<List<Boolean>>(emptyList()) }

    LaunchedEffect(logsList, periodsToShow, habitRow) {
        withContext(Dispatchers.Default) {
            val shownItems = periodsToShow.amount * when (periodsToShow.type) {
                logDisplayLength.DAYS -> 1
                logDisplayLength.WEEKS -> 7
                logDisplayLength.MONTHS -> now.month.length(now.toLocalDate().isLeapYear)
                // Fix applied here (see note below)
                logDisplayLength.YEARS -> if (now.toLocalDate().isLeapYear) 366 else 365
            }

            val computedIsDone = if (logsList.isNotEmpty()) {
                getCompletedPeriods(
                    beginingOfPeriods = beginingOfPeriods,
                    shownItems = shownItems,
                    habitRow = habitRow,
                    logsList = logsList,
                )
            } else mutableListOf()

            val computedGrace = computedIsDone.mapIndexed { index, _ ->
                determineGrace(
                    completionArrayIndex = index,
                    completionArray = computedIsDone as MutableList<Boolean>,
                    skipGrace = habitRow.checksSkipGrace,
                )
            }

            isDone = computedIsDone
            withinGrace = computedGrace
        }
    }

    val todayChecks = logsList.let{
        if(logsList.isEmpty()) return@let 0
        val lastest = logsList[0]

        if(isWithinPeriod(
                habitRow = habitRow,
                targetTimestamp = lastest.logTime,
                periodsAgo = 0L,
            )) {
            return@let lastest.checks
        }
        return@let 0
    }
    val todayDone = isDone.firstOrNull() ?: false
    val todayOverDone = todayChecks == habitRow.checksAmount

    val colorPrimary = MaterialTheme.colorScheme.primary
    val colorSecondary = MaterialTheme.colorScheme.secondary
    val colorTerniary = MaterialTheme.colorScheme.tertiary
    val colorSurfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val colorSurfaceContainter = MaterialTheme.colorScheme.surfaceContainer
    val stringComplete = stringResource(R.string.Complete)
    val stringNotComplete = stringResource(R.string.NotComplete)
    val stringOverDone = stringResource(R.string.OverDone)

    val progressData = remember {
        listOf(
            ChartData(colorPrimary, todayChecks.toFloat(), stringComplete),
            ChartData(
                colorSurfaceVariant,
                (habitRow.checksAmount.toFloat() - todayChecks.toFloat()),
                stringNotComplete
            )
        )
    }
    val doneChartData = remember {
        listOf(
            ChartData(
                color = if (todayOverDone) colorSecondary else colorSurfaceContainter,
                data = 2f,
                name = stringOverDone
            ),
            ChartData(
                color = if (todayDone) colorTerniary else colorSurfaceContainter,
                data = 1f,
                name = stringComplete
            ),
            ChartData(
                color = if (todayOverDone) colorSecondary else colorSurfaceContainter,
                data = 2f,
                name = stringOverDone
            ),
        )
    }

    UICard() {
        Row(
        ) {
            Column() {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    ArcChart(
                        data = doneChartData,
                        size = 64f + 8f + 4f,
                        strokeWith = 8.dp + 4.dp,
                        strokeSpaces = 16f + 8f + 4f,
                        startAngle = -140f,
                        arcAngle = 130
                    )
                    HabitCheckIcon(
                        icon = habitRow.icon,
                        colour = habitRow.colour,
                        complete = todayDone,
                        filled = false,
                        onClick = {},
                        size = 32.dp + 16.dp + 8.dp
                    )
                    ArcChart(
                        data = progressData,
                        size = 64f + 8f + 4f,
                        strokeWith = 8.dp + 4.dp,
                        strokeSpaces = 16f + 8f + 4f,
                        startAngle = 25f,
                        arcAngle = 155
                    )
                }
            }
            Column() {
                Text(
                    text = habitRow.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(habitRow.resetType.descriptorString),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal
                )
            }
            CompletionStreakUI(
                completion = isDone,
                grace = withinGrace,
                maxShownInputLength = 6,
                chipSize = 48.dp,
                spacesBetween = 4.dp,
                rowModifier = Modifier
                    .weight(1f, fill = false)
            )
            Text(
                text = "69%", //completion within period
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun OldBigLogDisplayCard(
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
                        Card(
                            colors = CardDefaults.cardColors().copy(
                                containerColor = FColour.Red.color
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.size(16.dp)
                        ) { }
                    } else {
                        val isWithinGrace = determineGrace(
                            completionArrayIndex = index,
                            completionArray = isDone,
                            skipGrace = habitRow.checksSkipGrace,
                        )

                        if (isWithinGrace) {
                            Card(
                                colors = CardDefaults.cardColors().copy(
                                    containerColor = FColour.Purple.color
                                ),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.size(16.dp)
                            ) { }
                        } else {
                            Card(
                                colors = CardDefaults.cardColors().copy(
                                    containerColor = FColour.Blue.color
                                ),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.size(16.dp)
                            ) { }
                        }
                    }
                },
            )

        }
    }
}