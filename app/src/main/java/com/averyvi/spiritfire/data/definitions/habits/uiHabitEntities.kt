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
    val resetType: ResetDaysType = ResetDaysType.DAILY,
    val resetDays: Int = 1,
    val resetHour: Int = 0,
    val resetMinute: Int = 0,
    val resetOffset: Int = 0,
    val checksAmount: Int = 0,
    val checksComplete: Int = 0,
    val checksType: Int = checkTypes.COMPLETIONS.stringRef,
    val checksSkipGrace: Int = 0,
    val priority: Int = 0,
    val cooldownHours: Int = 0,
    val cooldownMinutes: Int = 0,
    val cooldownSeconds: Int = 0,
    val difficulty: Int = 0,
    val tags: List<TagUIEntity>
) {
    companion object {
        val NO_HABIT = HabitRow(
            id = 0,
            name = "Flame Chase",
            description = "The Flame Chase was never about destruction. It was about breaking free, and trailblazing!",
            icon = R.drawable.flame_chase,
            colour = FColour.Purple.color,
            resetType = ResetDaysType.DAILY,
            resetDays = 1,
            resetHour = 12,
            resetMinute = 25,
            resetOffset = 0,
            checksAmount = 33550336,
            checksComplete = 12,
            checksType = checkTypes.COMPLETIONS.stringRef,
            checksSkipGrace = 2,
            priority = 7,
            cooldownHours = 0,
            cooldownMinutes = 0,
            cooldownSeconds = 3,
            difficulty = 5,
            tags = listOf(),
        )
    }
}

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
    val resetType: ResetDaysType = ResetDaysType.DAILY,
    val resetDays: Int = 1,
    val resetHour: Int = 0,
    val resetMinute: Int = 0,
    val resetOffset: Int = 0,
    val checksAmount: Int = 0,
    val checksComplete: Int = 0,
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