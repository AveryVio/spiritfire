package com.averyvi.spiritfire.data.definitions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.sources.HabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.map

class DetailedHabitAnalyticsViewModel(
    private val habitRepository: HabitRepository,
    private val habitFilterViewModel: HabitFilterViewModel,
    var logSortingFiltering: LogSortingFiltering,
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val habit: StateFlow<HabitRow> = if(habitFilterViewModel.shownDetail.value != 0) {
        habitFilterViewModel.shownDetail
            .flatMapLatest {
                habitRepository.getSelectHabits(listOf(it)).map { list ->
                    list.first()
                }
            }
    } else {
        object : Flow<HabitRow> {
            override suspend fun collect(collector: FlowCollector<HabitRow>) {
                HabitRow.NO_HABIT
            }
        }
    }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HabitRow.NO_HABIT
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val filtredLogs: StateFlow<List<HabitLogItem>> = habit
        .flatMapLatest { habitVal ->
            if (habitVal.id == 0) {
                flowOf(emptyList())
            } else {
                habitRepository.getFilteredAndSortedLogs(
                    logSortingFiltering = logSortingFiltering,
                    selectIds = listOf(habitVal.id)
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
