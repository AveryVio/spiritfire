package com.averyvi.spiritfire.data.definitions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.sources.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.toHabitRow
import com.averyvi.spiritfire.data.definitions.sortingfiltering.ColumnType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.FilteringType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering.Companion.addFilter
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering.Companion.addSorting
import com.averyvi.spiritfire.data.sources.HabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class OverviewViewModel(
    private val habitRepository: HabitRepository,
    private val habitFilterViewModel: HabitFilterViewModel,
    val sortingFiltering: SortingFiltering,
) : ViewModel() {
    private val _allItems: StateFlow<List<HabitRow>> = habitRepository.getFilteredAndSortedHabits(sortingFiltering)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val displayedHabits: StateFlow<List<HabitRow>> = combine(
        _allItems,
        habitFilterViewModel.selectedFilters
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
                habitRepository.getAllLogsFromListOfHabits(habits.map { it.id })
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}