package com.averyvi.spiritfire.ui.screens

import android.icu.util.Calendar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.approachLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.EmojiType
import com.averyvi.spiritfire.data.definitions.habits.EmojiType.getEmoji
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.definitions.ui.AllHabitsViewModel
import com.averyvi.spiritfire.data.definitions.ui.ChartData
import com.averyvi.spiritfire.data.definitions.ui.DetailedHabitAnalyticsViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.data.transformations.contractPeriods
import com.averyvi.spiritfire.data.transformations.determineGrace
import com.averyvi.spiritfire.data.transformations.getCompletedPeriods
import com.averyvi.spiritfire.ui.appUI.top.HabitAppBar
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.basic.HabitDPTPills
import com.averyvi.spiritfire.ui.basic.ShowRowsOfItems
import com.averyvi.spiritfire.ui.basic.SmallPill
import com.averyvi.spiritfire.ui.basic.SquareChip
import com.averyvi.spiritfire.ui.basic.WidthFlexibleChip
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow
import com.averyvi.spiritfire.ui.components.BigLogDisplayCard
import com.averyvi.spiritfire.ui.components.LinearChart
import com.averyvi.spiritfire.ui.components.PieChartWithLabels
import com.averyvi.spiritfire.ui.components.TransparentLogDisplayBlock
import com.averyvi.spiritfire.ui.components.UICard
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration.Companion.days

@Composable
fun DetailedHabitAnalyticsScreen(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository,
    outerPadding: PaddingValues
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
    val detailedHabitAnalyticsViewModel: DetailedHabitAnalyticsViewModel =
        viewModel(factory = detailedHabitAnalyticsVMFactory)
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

    val scope = rememberCoroutineScope()

    val variousContentScroll = rememberScrollState()
    val isDragged by variousContentScroll.interactionSource.collectIsDraggedAsState()

    val descBlockedVisible = remember { mutableStateOf(false) }
    val daysBlockedVisible = remember { mutableStateOf(false) }

    val showDays = remember { mutableStateOf(periodsToShow.MONTH) }

    LaunchedEffect(isDragged) {
        if (isDragged && descBlockedVisible.value) {
            descBlockedVisible.value = false
            daysBlockedVisible.value = false
        }
    }

    Scaffold(
        modifier = Modifier.padding(outerPadding),
        topBar = {
            HabitAppBar(

                habitRow = habitRow,
                data = periodData,
                onNameClick = {
                    scope.launch {
                        descBlockedVisible.value = !descBlockedVisible.value
                        variousContentScroll.animateScrollTo(0)
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(variousContentScroll)
                .padding(top = 8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            if (descBlockedVisible.value) {
                                descBlockedVisible.value = false
                            }
                        }
                    )
                },
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedVisibility(
                visible = descBlockedVisible.value
            ) {
                AnalyticsHabitDescTags(
                    habitRow = habitRow
                )
            }

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
                },
                onChartClick = {
                    scope.launch {
                        daysBlockedVisible.value = !daysBlockedVisible.value
                        variousContentScroll.animateScrollTo(0)
                    }
                }
            )
            //period completion UI
            AnimatedVisibility(
                visible = daysBlockedVisible.value
            ) {
                AnalyticsHabitLogsGrid(
                    habitRow = habitRow,
                    logsList = filtredLogs,
                    periodsToShow = showDays.value,
                )
            }

            Spacer(modifier = Modifier.height(128.dp + 32.dp))
        }
    }
}

@Composable
fun AnalyticsHabitDescTags(
    habitRow: HabitRow
) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (habitRow.description.isNotBlank()) {
            Text(
                text = habitRow.description,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                Modifier.height(8.dp)
            )
        }
        HabitDPTPills(
            contractionLevel = 1,
            difficulty = habitRow.difficulty,
            priority = habitRow.priority,
            tags = habitRow.tags
        )
    }
}

@Composable
fun AnalyticsHabitComletionHint(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = modifier,
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
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
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
        verticalArrangement = Arrangement.spacedBy(4.dp + 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.Complete) + " " + stringResource(R.string.Today),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
            AnalyticsHabitComletionHint(
                data = data,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        LinearChart(
            data = data,
            size = 256f + 64f,
            strokeWith = 32.dp + 2.dp,
            strokeSpaces = 64f + 32f + 16f + 4f,
            labels = true,
            modifier = Modifier.padding(8.dp)
        )
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
    changeShowDays: (periodsToShow) -> Unit,
    onChartClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.Complete) + " " + stringResource(R.string.WithinPeriod),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
            AnalyticsHabitComletionHint(
                data = data,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        AnalyticsHabitLogPeriodSelector(
            habitRow = habitRow,
            showDays = showDays,
            changeShowDays = changeShowDays
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        onClick = onChartClick
                    ),
            ) {
                PieChartWithLabels(
                    data = data,
                    size = 128f + 16f + 8f,
                    strokeWith = 32.dp,
                    strokeSpaces = 32f + 4f
                )
                Text(
                    text = 69.toString() + "%",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun AnalyticsHabitLogsGrid(
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

    val combinedPeriods = contractPeriods(
        isDone = isDone,
        graceGroup = graceGroup,
        periodsToShow = periodsToShow,
    )

    var itemsRemaining = 0
    val chipSize = 52.dp
    
    val chipsInRow = when(habitRow.resetType) {
        ResetDaysType.DAILY -> 7
        ResetDaysType.WEEKLY -> 7
        ResetDaysType.MONTHLY -> 4
        ResetDaysType.YEARLY -> 8
        ResetDaysType.CUSTOM_DAYS -> 7
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 8.dp).padding(top = 16.dp)
    ) {
        var itemsRemaining = 0
        while ( itemsRemaining < combinedPeriods.size ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (row in (0..<chipsInRow)) {
                    if (itemsRemaining < combinedPeriods.size) {
                        if (combinedPeriods[itemsRemaining]) {
                            WidthFlexibleChip(
                                color = MaterialTheme.colorScheme.primary,
                                height = chipSize,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            when(periodsToShow.type) {
                                logDisplayLength.DAYS -> {
                                    val isWithinGrace = determineGrace(
                                        completionArrayIndex = itemsRemaining,
                                        completionArray = combinedPeriods,
                                        skipGrace = habitRow.checksSkipGrace,
                                    )

                                    if (isWithinGrace) {
                                        WidthFlexibleChip(
                                            color = MaterialTheme.colorScheme.secondary,
                                            height = chipSize,
                                            modifier = Modifier.weight(1f)
                                        )
                                    } else {
                                        WidthFlexibleChip(
                                            color = MaterialTheme.colorScheme.surfaceVariant,
                                            height = chipSize,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                                logDisplayLength.WEEKS -> {
                                    val isWithinGrace = determineGrace(
                                        completionArrayIndex = itemsRemaining,
                                        completionArray = combinedPeriods,
                                        skipGrace = habitRow.checksSkipGrace,
                                    )

                                    if (isWithinGrace) {
                                        WidthFlexibleChip(
                                            color = MaterialTheme.colorScheme.secondary,
                                            height = chipSize,
                                            modifier = Modifier.weight(1f)
                                        )
                                    } else {
                                        WidthFlexibleChip(
                                            color = MaterialTheme.colorScheme.surfaceVariant,
                                            height = chipSize,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                                else -> {
                                    WidthFlexibleChip(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        height = chipSize,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                        itemsRemaining++
                    } else { break }
                }
            }
        }
    }
}

@Composable
fun AnalyticsHabitCompletionRating(
    completionPercent: Float,
    difficulty: Int,
    priority: Int,
    fineTuning: Float,
    modifier: Modifier = Modifier,
) {
    var result = 0f
    result = completionPercent * (difficulty.toFloat() + priority.toFloat() * 2) / ( fineTuning * 19)

    val resultingPercentage = (result * 100).toInt()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = EmojiType.getEmoji(
                completionPercentage = resultingPercentage,
                difficulty = difficulty
            ),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = resultingPercentage.toString(),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold
        )

    }
}