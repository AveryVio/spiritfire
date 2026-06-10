package com.averyvi.spiritfire.data.definitions.habits

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.averyvi.spiritfire.R
import java.util.Date

data class HabitRow(
    val id: Int = 0,
    val name: String = "Flame",
    val description: String = "Something to do.",
    val icon: Int = R.drawable.r_outline_dark_mode_2,
    val colour: Color = FColour.Red.color,
    val resetType: ResetDaysType = ResetDays.DAILY.resetType,
    val resetDays: Int = ResetDays.DAILY.resetDays,
    val resetHour: Int = 0,
    val resetMinute: Int = 0,
    val resetOffset: Int = 0,
    val checksAmount: Int = 0,
    val checksComplete: Int = 0,
    val checksType: Int = checkTypes.COMPLETIONS.stringRef,
    val checksSkipGrace: Int = 0,
    val checksNames: String = "",
    val priority: Int = 0,
    val cooldownHours: Int = 0,
    val cooldownMinutes: Int = 0,
    val cooldownSeconds: Int = 0,
    val difficulty: Int = 0,
    val tags: List<TagUIEntity>
)

data class HabitForList(
    val id: Int = 0,
    val name: String = "Flame",
    val icon: Int = R.drawable.r_outline_dark_mode_2,
    val colour: Int = FColour.Red.color.toArgb(),
)

data class HabitBasics(
    val id: Int = 0,
    val name: String = "Flame",
    val icon: Int = R.drawable.r_outline_dark_mode_2,
    val colour: Int = FColour.Red.color.toArgb(),
    val checksAmount: Int = 0,
    val checksComplete: Int = 0,
    val checksType: Int = checkTypes.COMPLETIONS.stringRef,
    val priority: Int = 0,
    val difficulty: Int = 0,
)

data class HabitCheckingUI(
    val id: Int = 0,
    val resetType: ResetDaysType = ResetDays.DAILY.resetType,
    val resetDays: Int = ResetDays.DAILY.resetDays,
    val resetHour: Int = 0,
    val resetMinute: Int = 0,
    val resetOffset: Int = 0,
    val checksAmount: Int = 0,
    val checksComplete: Int = 0,
    val checksNames: String = "",
    val cooldownHours: Int = 0,
    val cooldownMinutes: Int = 0,
    val cooldownSeconds: Int = 0,
)

data class HabitLogItem(
    val logTime: Long = Date(0).time,
    val checks: Int = 0,
    val habit: Int = 0,
)

data class TagUIEntity(
    val id: Int = 0,
    val name: String = "",
    val colour: Int = FColour.Red.color.toArgb(),
)