package com.averyvi.spiritfire.data.definitions

import java.util.Collections.emptyList

data class Habit(
    val name: String = "",
    val description: String = "",

    val icon: HabitIcon = HabitIcon.moon,
    val color: HabitColor = HabitColor.Purple,

    val resetInterval: ResetDaysInterval = ResetDaysInterval.DAILY,
    val resetTime: ResetTime = ResetTime.MIDNIGHT,

    val priority: Int = 16,

    val habitStepsAmount: String = "",
    val habitStepsComplete: String = "",
    val habitStepsStrings: MutableList<String> = emptyList(),
)