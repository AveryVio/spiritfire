package com.averyvi.spiritfire.data.definitions.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering.Companion.addFilter
import com.averyvi.spiritfire.data.sources.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HabitFilterViewModel(private val habitRepository: HabitRepository) : ViewModel() {
    val filterItems: StateFlow<List<HabitForList>> = habitRepository.getHabitsForList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedHabits = MutableStateFlow<Set<Int>>(emptySet())
    val selectedHabits = _selectedHabits.asStateFlow()

    private val _sortingFiltering = MutableStateFlow(HabitSortingFiltering.TESTING)
    val sortingFiltering = _sortingFiltering.asStateFlow()

    fun toggleFilter(habitId: Int) {
        _selectedHabits.update { currentSelected ->
            if (currentSelected.contains(habitId)) {
                currentSelected - habitId
            } else {
                currentSelected + habitId
            }
        }
    }

    fun selectFilter(habitId: Int) {
        _selectedHabits.update { currentSelected ->
            if (!currentSelected.contains(habitId)) {
                currentSelected + habitId
            } else {
                currentSelected
            }
        }
    }

    fun unselectFilter(habitId: Int) {
        _selectedHabits.update { currentSelected ->
            if (currentSelected.contains(habitId)) {
                currentSelected - habitId
            } else {
                currentSelected
            }
        }
    }

    fun getUsedFilters(): Set<Int> {
        return _selectedHabits.value
    }

    fun updateSortingFiltering(newState: HabitSortingFiltering) {
        _sortingFiltering.value = newState
    }
}