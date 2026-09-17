package com.averyvi.spiritfire.data.definitions.ui

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.data.transformations.determineGrace
import com.averyvi.spiritfire.data.transformations.getCompletedPeriods
import com.averyvi.spiritfire.data.transformations.isWithinPeriod
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import java.time.ZoneId
import java.time.ZonedDateTime

class OverviewViewModel(
    private val habitRepository: HabitRepository,
    private val habitFilterViewModel: HabitFilterViewModel,
    var logSortingFiltering: LogSortingFiltering,
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _allItems: StateFlow<List<HabitRow>> = habitFilterViewModel.sortingFiltering
        .flatMapLatest { currentFilters ->
            habitRepository.getFilteredAndSortedHabits(currentFilters)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val displayedHabits: StateFlow<List<HabitRow>> = combine(
        _allItems,
        habitFilterViewModel.selectedHabits
    ) { allHabits, selectedIds ->
        if (selectedIds.isNotEmpty()) {
            allHabits.filter { habit -> selectedIds.contains(habit.id) }
        } else {
            emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    val filtredLogs: StateFlow<List<HabitLogItem>> = displayedHabits
        .flatMapLatest { habits ->
            if (habits.isEmpty()) {
                flowOf(emptyList())
            } else {
                habitRepository.getFilteredAndSortedLogs(
                    logSortingFiltering = logSortingFiltering,
                    selectIds = habits.map { it.id }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _showDays = MutableStateFlow(periodsToShow.WEEK)
    val showDays: StateFlow<periodsToShow> = _showDays.asStateFlow()
    fun changeShownDays(newValue: periodsToShow) { _showDays.value = newValue }

    data class HabitCardUiState(
        var habitRow: HabitRow,
        var isDone: List<Boolean>,
        var withinGrace: List<Boolean>,
        var todayChecks: Int
    )

    val overviewUiState: StateFlow<List<HabitCardUiState>> = combine(
        displayedHabits,
        filtredLogs
    ) { habits, logs ->
        var output = listOf<HabitCardUiState>()

        val beginningOfPeriods = 0
        val logsByHabit = logs.groupBy { it.habit }
        val periodsToShow = _showDays.value
        val now = ZonedDateTime.now(ZoneId.systemDefault())

        return@combine habits.map { habit ->

            val habitLogs = logsByHabit[habit.id] ?: emptyList()

            val shownItems = periodsToShow.amount * when (periodsToShow.type) {
                logDisplayLength.DAYS -> 1
                logDisplayLength.WEEKS -> 7
                logDisplayLength.MONTHS -> now.month.length(now.toLocalDate().isLeapYear)
                // Fix applied here (see note below)
                logDisplayLength.YEARS -> if (now.toLocalDate().isLeapYear) 366 else 365
            }

            val computedIsDone = getCompletedPeriods(
                beginingOfPeriods = beginningOfPeriods,
                shownItems = shownItems,
                habitRow = habit,
                logsList = habitLogs,
            )

            val computedGrace = List(computedIsDone.size) { index ->
                determineGrace(
                    completionArrayIndex = index,
                    completionArray = computedIsDone,
                    skipGrace = habit.checksSkipGrace,
                )
            }

            val computedTodayChecks = if (logsByHabit.isNotEmpty()) {
                val todayLogs = habitLogs
                    .filter {
                        isWithinPeriod(
                            habitRow = habit,
                            targetTimestamp = it.logTime,
                            periodsAgo = 0L,
                        )
                    }
                todayLogs.sumOf { it.checks }
            } else 0

            HabitCardUiState(
                habitRow = habit,
                isDone = computedIsDone,
                withinGrace = computedGrace,
                todayChecks = computedTodayChecks
            )
        }
    }.flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}