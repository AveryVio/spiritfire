package com.averyvi.spiritfire.old.data.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.averyvi.spiritfire.old.data.definitions.Habit
import com.averyvi.spiritfire.old.data.definitions.HabitColor
import com.averyvi.spiritfire.old.data.definitions.HabitIcon
import com.averyvi.spiritfire.old.data.definitions.ResetDaysInterval
import com.averyvi.spiritfire.old.data.definitions.ResetDaysIntervalUnit
import com.averyvi.spiritfire.old.data.definitions.ResetTime
import com.averyvi.spiritfire.old.data.definitions.habitConsts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.String

class SingleHabitViewModel(): ViewModel() {
    private val _habit = MutableStateFlow(Habit())
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

        newStepsAmount: String = _habit.value.stepsAmount,
        newStepsComplete: String = _habit.value.stepsComplete,
        newStepsStrings: List<String> = _habit.value.stepsStrings,
    ) {
        _habit.update { it.copy(
            name = newName,
            description = newDescription,
            icon = newIcon,
            color = newColor,
            resetInterval = ResetDaysInterval(
                interval_unit = newResetIntervalUnit,
                newResetIntervalValue
            ),
            resetTime = newResetTime,
            priority = newPriority,
            stepsAmount = newStepsAmount,
            stepsComplete = newStepsComplete,
            stepsStrings = newStepsStrings,
        )}
    }

    fun resetHabit() {
        _habit.update { it.copy(
            name = "",
            description = "",
            resetInterval = ResetDaysInterval.DAILY,
            resetTime = ResetTime.MIDNIGHT,
            priority = 16,
            stepsAmount = "",
            stepsComplete = "",
            stepsStrings = List(habitConsts.maxSteps) { index -> "" },
        )}
    }

    fun changeHabitString(index: Int, string: String){
        val updatedStrings = _habit.value.stepsStrings.toMutableList()
        updatedStrings[index] = string
        _habit.update {
            _habit.value.copy(stepsStrings = updatedStrings)
        }
    }
}