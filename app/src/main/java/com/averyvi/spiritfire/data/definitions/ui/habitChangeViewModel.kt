package com.averyvi.spiritfire.data.definitions.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.habits.TagUIEntity
import com.averyvi.spiritfire.data.definitions.habits.checkTypes
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.sources.HabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HabitChangeViewModel(
    private val habitRepository: HabitRepository,
    private val habitFilterViewModel: HabitFilterViewModel,
    private val changingHabit: HabitRow = HabitRow.NO_HABIT
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _editedHabit = MutableStateFlow<HabitRow>(changingHabit)
    val editedHabit = _editedHabit.asStateFlow()

    fun changeValue(
        name: String = editedHabit.value.name,
        description: String = editedHabit.value.description,
        icon: Int = editedHabit.value.icon,
        colour: Color = editedHabit.value.colour,
        resetType: ResetDaysType = editedHabit.value.resetType,
        resetDays: Int = editedHabit.value.resetDays,
        resetHour: Int = editedHabit.value.resetHour,
        resetMinute: Int = editedHabit.value.resetMinute,
        resetOffset: Int = editedHabit.value.resetOffset,
        checksAmount: Int = editedHabit.value.checksAmount,
        checksComplete: Int = editedHabit.value.checksComplete,
        checksType: Int = editedHabit.value.checksType,
        checksSkipGrace: Int = editedHabit.value.checksSkipGrace,
        priority: Int = editedHabit.value.priority,
        cooldownHours: Int = editedHabit.value.cooldownHours,
        cooldownMinutes: Int = editedHabit.value.cooldownMinutes,
        cooldownSeconds: Int = editedHabit.value.cooldownSeconds,
        difficulty: Int = editedHabit.value.difficulty,
        tags: List<TagUIEntity> = editedHabit.value.tags
    ) {
        HabitRow(
            id = editedHabit.value.id,
            name = name,
            icon = icon,
            description = description,
            colour = colour,
            resetType = resetType,
            resetDays = resetDays,
            resetHour = resetHour,
            resetMinute = resetMinute,
            resetOffset = resetOffset,
            checksAmount = checksAmount,
            checksComplete = checksComplete,
            checksType = checksType,
            checksSkipGrace = checksSkipGrace,
            priority = priority,
            cooldownHours = cooldownHours,
            cooldownMinutes = cooldownMinutes,
            cooldownSeconds = cooldownSeconds,
            difficulty = difficulty,
            tags = tags,
        ).also { _editedHabit.value = it }
    }
}
