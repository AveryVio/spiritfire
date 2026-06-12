package com.averyvi.spiritfire.data.definitions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.toHabitRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class OverviewViewModel(
    private val habitDAO: HabitRegistryUserDao,
    private val habitFilterViewModel: HabitFilterViewModel
) : ViewModel() {
    private val _AllItems: StateFlow<List<HabitRow>> = habitDAO.getAllHabits()
        .map { habitDbList ->
            habitDbList.map { habitWithTags -> habitWithTags.toHabitRow() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val displayedHabits: StateFlow<List<HabitRow>> = combine(
        _AllItems,
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