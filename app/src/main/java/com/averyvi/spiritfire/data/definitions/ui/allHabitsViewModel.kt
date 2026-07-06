package com.averyvi.spiritfire.data.definitions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.sources.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AllHabitsViewModel(
    private val habitRepository: HabitRepository,
    private val habitFilterViewModel: HabitFilterViewModel
) : ViewModel() {
    private val _allItems: StateFlow<List<HabitRow>> = habitRepository.getAllHabits()
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
}
