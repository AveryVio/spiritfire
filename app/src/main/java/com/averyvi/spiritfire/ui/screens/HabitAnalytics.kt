package com.averyvi.spiritfire.ui.screens

import android.icu.util.Calendar
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
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

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val showDays = remember { mutableStateOf(periodsToShow.MONTH) }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(habitRow.icon),
                modifier = Modifier.size(32.dp),
                tint = habitRow.colour,
                contentDescription = null,
            )
            Text(
                text = habitRow.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = habitRow.colour
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = when(habitRow.resetType) {
                    ResetDaysType.CUSTOM_DAYS -> {
                        stringResource(R.string.TaskEvery) + " " + habitRow.resetDays + " " + stringResource(
                            habitRow.resetType.descriptorString
                        )
                    }
                    else -> {
                        stringResource(habitRow.resetType.descriptorString) + " " + stringResource(R.string.Task)
                    }
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if(habitRow.description.isNotBlank()) {
            Text(
                text = habitRow.description,
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
        //motivator lines ig
        val timeSelectorScrollState = rememberScrollState()
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(R.string.PeriodToShow) + " :",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 2.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.horizontalScroll(
                    state = timeSelectorScrollState,
                )
            ) {
                periodsToShow.entries.forEachIndexed { index, pair ->
                    SmallPill(
                        onClick = {
                            showDays.value = pair
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
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val chartDataList = listOf(
                    ChartData(MaterialTheme.colorScheme.primary, 2f),
                    ChartData(MaterialTheme.colorScheme.secondary, 1f),
                    ChartData(MaterialTheme.colorScheme.surfaceContainer, 2f),
                )
                PieChartWithLabels(
                    data = chartDataList,
                    size = 66f,
                    strokeWith = 12.dp,
                    strokeSpaces = 32f
                )
                Column(
                ) {
                    chartDataList.forEachIndexed { index, data ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SquareChip(
                                color = data.color,
                                size = 20.dp
                            )
                            Text(
                                text = stringResource(
                                    when (index) {
                                        0 -> R.string.Complete
                                        1 -> R.string.WithinGrace
                                        else -> R.string.NotComplete
                                    }
                                )
                            )
                        }
                    }
                }
            }
            Row() {
                Text(
                    text = stringResource(R.string.Completion) + " " + 69 + " %",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        //period completion UI
        Column() {
            TransparentLogDisplayBlock(
                habitRow = habitRow,
                logsList = filtredLogs,
                periodsToShow = showDays.value
            )
        }
        //
    }
}