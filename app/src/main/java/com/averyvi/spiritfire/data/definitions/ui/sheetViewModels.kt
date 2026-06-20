package com.averyvi.spiritfire.data.definitions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.sources.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HabitFilterViewModel(private val habitDAO: HabitRegistryUserDao) : ViewModel() {
    val filterItems: StateFlow<List<HabitForList>> = habitDAO.getAllHabitsForList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedFilters = MutableStateFlow<Set<Int>>(emptySet())
    val selectedFilters = _selectedFilters.asStateFlow()

    fun toggleFilter(habitId: Int) {
        _selectedFilters.update { currentSelected ->
            if (currentSelected.contains(habitId)) {
                currentSelected - habitId
            } else {
                currentSelected + habitId
            }
        }
    }

    fun selectFilter(habitId: Int) {
        _selectedFilters.update { currentSelected ->
            if (!currentSelected.contains(habitId)) {
                currentSelected + habitId
            } else {
                currentSelected
            }
        }
    }

    fun unselectFilter(habitId: Int) {
        _selectedFilters.update { currentSelected ->
            if (currentSelected.contains(habitId)) {
                currentSelected - habitId
            } else {
                currentSelected
            }
        }
    }

    fun getUsedFilters(): Set<Int> {
        return _selectedFilters.value
    }
}