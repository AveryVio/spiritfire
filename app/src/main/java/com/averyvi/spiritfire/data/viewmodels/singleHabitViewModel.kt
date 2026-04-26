package com.averyvi.spiritfire.data.viewmodels

import androidx.lifecycle.ViewModel
import com.averyvi.spiritfire.data.definitions.Habit
import com.averyvi.spiritfire.data.definitions.HabitColor
import com.averyvi.spiritfire.data.definitions.HabitIcon
import com.averyvi.spiritfire.data.definitions.ResetDaysInterval
import com.averyvi.spiritfire.data.definitions.ResetDaysIntervalUnit
import com.averyvi.spiritfire.data.definitions.ResetTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Collections.emptyList
import kotlin.String

class SingleHabitViewModel(): ViewModel() {
    private val _habit = MutableStateFlow( Habit() )
    val habit: StateFlow<Habit> = _habit.asStateFlow()

    fun changeHabitValue(
        newName: String = _habit.value.name,
        newDescription: String = _habit.value.description,

        newIcon: HabitIcon = _habit.value.icon,
        newColor: HabitColor = _habit.value.color,

        newResetIntervalUnit: ResetDaysIntervalUnit = _habit.value.resetInterval.interval_unit,
        newResetIntervalValue: String = _habit.value.resetInterval.interval_value,
        newResetTime: ResetTime = _habit.value.resetTime,

        newPriority: Int = _habit.value.priority,

        newHabitStepsAmount: String = _habit.value.habitStepsAmount,
        newHabitStepsComplete: String = _habit.value.habitStepsComplete,
        newHabitStepsStrings: MutableList<String> = _habit.value.habitStepsStrings,
    ) {
        _habit.update { it.copy(
            name = newName,
            description = newDescription,
            icon = newIcon,
            color = newColor,
            resetInterval = ResetDaysInterval(interval_unit = newResetIntervalUnit, newResetIntervalValue),
            resetTime = newResetTime,
            priority = newPriority,
            habitStepsAmount = newHabitStepsAmount,
            habitStepsComplete = newHabitStepsComplete,
            habitStepsStrings = newHabitStepsStrings,
        )}
    }

    fun resetHabit() {
        _habit.update { it.copy(
            name = "",
            description = "",
            resetInterval = ResetDaysInterval.DAILY,
            resetTime = ResetTime.MIDNIGHT,
            priority = 16,
            habitStepsAmount = "",
            habitStepsComplete = "",
            habitStepsStrings = emptyList(),
        )}
    }

    fun addNewHabitString(){
        _habit.value.habitStepsStrings.add("")
    }

    fun removeLastHabitString(){
        _habit.value.habitStepsStrings.removeAt(_habit.value.habitStepsStrings.size - 1)
    }
}