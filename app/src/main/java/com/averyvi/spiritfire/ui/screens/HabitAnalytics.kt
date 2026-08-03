package com.averyvi.spiritfire.ui.screens

import android.icu.util.Calendar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.definitions.ui.AllHabitsViewModel
import com.averyvi.spiritfire.data.definitions.ui.ChartData
import com.averyvi.spiritfire.data.definitions.ui.DetailedHabitAnalyticsViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.data.transformations.determineGrace
import com.averyvi.spiritfire.data.transformations.getCompletedPeriods
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.basic.HabitDPTPills
import com.averyvi.spiritfire.ui.basic.ShowRowsOfItems
import com.averyvi.spiritfire.ui.basic.SmallPill
import com.averyvi.spiritfire.ui.basic.SquareChip
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow
import com.averyvi.spiritfire.ui.components.BigLogDisplayCard
import com.averyvi.spiritfire.ui.components.LinearChart
import com.averyvi.spiritfire.ui.components.PieChartWithLabels
import com.averyvi.spiritfire.ui.components.TransparentLogDisplayBlock
import com.averyvi.spiritfire.ui.components.UICard
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration.Companion.days

@Composable
fun DetailedHabitAnalyticsScreen(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository
) {
    val detailedHabitAnalyticsVMFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailedHabitAnalyticsViewModel(
                habitRepository = habitRepository,
                habitFilterViewModel = habitFilterViewModel,
                logSortingFiltering = LogSortingFiltering.TESTING
            ) as T
        }
    }
    val detailedHabitAnalyticsViewModel: DetailedHabitAnalyticsViewModel = viewModel(factory = detailedHabitAnalyticsVMFactory)
    val habitRow = detailedHabitAnalyticsViewModel.habit.collectAsState().value
    val filtredLogs = detailedHabitAnalyticsViewModel.filtredLogs.collectAsState().value

    val todayData = listOf(
        ChartData(MaterialTheme.colorScheme.primary, 19f),
        ChartData(MaterialTheme.colorScheme.surfaceVariant, 11f),
    )
    val periodData = listOf(
        ChartData(MaterialTheme.colorScheme.primary, 2f),
        ChartData(MaterialTheme.colorScheme.secondary, 1f),
        ChartData(MaterialTheme.colorScheme.surfaceVariant, 2f),
    )

    Column() {
        val showDays = remember { mutableStateOf(periodsToShow.MONTH) }

        AnalyticsHabitNameBlock(
            habitRow = habitRow,
            data = periodData,
        )

        val variousContentScroll = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll( variousContentScroll ).padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnalyticsHabitDescTags(
                habitRow = habitRow
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            // today
            AnalyticsHabitDayCompletionRundown(
                habitRow = habitRow,
                data = todayData,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            //streak

            // over time
            AnalyticsHabitLogsShortRundown(
                habitRow = habitRow,
                data = periodData,
                showDays = showDays.value,
                changeShowDays = {
                    showDays.value = it
                }
            )
            //period completion UI
            TransparentLogDisplayBlock(
                habitRow = habitRow,
                logsList = filtredLogs,
                periodsToShow = showDays.value
            )
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
        }
    }
}

@Composable
fun AnalyticsHabitNameBlock(
    habitRow: HabitRow,
    data: List<ChartData>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        ) {
            Icon(
                painter = painterResource(habitRow.icon),
                modifier = Modifier.size(48.dp),
                tint = habitRow.colour,
                contentDescription = null,
            )
            Column() {
                Text(
                    text = habitRow.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            // todo edit button
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    }
}

@Composable
fun AnalyticsHabitDescTags(
    habitRow: HabitRow
) {
    if(habitRow.description.isNotBlank()) {
        Text(
            text = habitRow.description,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(
            Modifier.height(4.dp)
        )
    }
    HabitDPTPills(
        contractionLevel = 1,
        difficulty = habitRow.difficulty,
        priority = habitRow.priority,
        tags = habitRow.tags
    )
}

@Composable
fun AnalyticsHabitComletionHint(
    data: List<ChartData>
) {
    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        val dataHintVisible = remember { mutableStateOf(false) }
        Card(
            onClick = { dataHintVisible.value = true },
        ) {
            Text(
                text = "?",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
            )
        }
        DropdownMenu(
            expanded = dataHintVisible.value,
            onDismissRequest = { dataHintVisible.value = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(8.dp)
            ) {
                data.forEachIndexed { index, data ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(
                                when (index) {
                                    0 -> R.string.Complete
                                    1 -> R.string.WithinGrace
                                    else -> R.string.NotComplete
                                }
                            ),
                            fontWeight = FontWeight.SemiBold,
                            color = data.color
                        )
                        SquareChip(
                            color = data.color,
                            size = 20.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnalyticsHabitDayCompletionRundown(
    data: List<ChartData>,
    habitRow: HabitRow,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.Complete) + " " + stringResource(R.string.Today),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        LinearChart(
            data = data,
            size = 256f,
            strokeWith = 32.dp,
            strokeSpaces = 64f + 32f + 8f + 4f,
            labels = true
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.ResetAt),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal
                    )
                    val resetTime = listOf(
                        habitRow.resetHour,
                        habitRow.resetMinute
                    )
                    resetTime.forEachIndexed { index, value ->
                        Card(
                            modifier = Modifier.padding(2.dp),
                        ) {
                            Text(
                                text = value.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                fontFamily = FontFamily.Monospace, // todo add monospace font
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                        if (index < (resetTime.size - 1)) {
                            Text(
                                text = ":",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
                // todo time until reset
            }
            Spacer(Modifier.weight(2f))
            AnalyticsHabitComletionHint(
                data = data
            )
        }
    }
}

@Composable
fun AnalyticsHabitLogPeriodSelector(
    habitRow: HabitRow,
    showDays: periodsToShow,
    changeShowDays: (periodsToShow) -> Unit
) {
    val timeSelectorScrollState = rememberScrollState()
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.horizontalScroll(
            state = timeSelectorScrollState,
        )
    ) {
        periodsToShow.entries.forEachIndexed { index, pair ->
            SmallPill(
                color = if(showDays == pair) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                onClick = { changeShowDays(pair) }
            ) {
                val isPlural = pair.amount > 1
                Text(
                    text = pair.amount.toString() + " " + if (isPlural) {
                        stringResource(pair.type.string2Plural)
                    } else {
                        stringResource(pair.type.string2Singular)
                    }
                )
            }
        }
    }
}

@Composable
fun AnalyticsHabitLogsShortRundown(
    data: List<ChartData>,
    habitRow: HabitRow,
    showDays: periodsToShow,
    changeShowDays: (periodsToShow) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp + 2.dp)
    ) {
        Text(
            text = stringResource(R.string.Complete) + " " + stringResource(R.string.WithinPeriod),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        AnalyticsHabitLogPeriodSelector(
            habitRow = habitRow,
            showDays = showDays,
            changeShowDays = changeShowDays
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                PieChartWithLabels(
                    data = data,
                    size = 122f,
                    strokeWith = 24.dp,
                    strokeSpaces = 32f
                )
                Text(
                    text = 69.toString() + "%",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // todo add something

            AnalyticsHabitComletionHint(
                data = data
            )
        }
    }
}