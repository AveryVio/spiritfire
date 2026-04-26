package com.averyvi.spiritfire.data.definitions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.util.Collections.emptyList

data class Habit(
    val name: String = "",
    val description: String = "",

    val icon: HabitIcon = HabitIcon.moon,
    val color: HabitColor = HabitColor.Purple,

    val resetInterval: ResetDaysInterval = ResetDaysInterval.DAILY,
    val resetTime: ResetTime = ResetTime.MIDNIGHT,

    val priority: Int = 16,

    val stepsAmount: String = "",
    val stepsComplete: String = "",
    val stepsStrings: List<String> = List(habitConsts.maxSteps) { "" },
)