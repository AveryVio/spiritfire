package com.averyvi.spiritfire.data.definitions.habits

import androidx.compose.ui.graphics.Color
import com.averyvi.spiritfire.R

enum class FColour(val color: Color){
    Red(color = Color.hsv(0f, 0.55f, 1f)),
    Green(color = Color.hsv(120f, 0.55f, 0.8f)),
    Blue(color = Color.hsv(224f, 0.55f, 1f)),
    Yellow(color = Color.hsv(50f, 0.5f, 0.9f)),
    Purple(color = Color.hsv(256f, 0.66f, 1f)),
}

data class ResetDays(
    val resetType: ResetDaysType,
    val resetDays: Int,
) {
    companion object {
        val DAILY =
            ResetDays(
                resetType = ResetDaysType.DAILY,
                resetDays = 0
            )
        val WEEKLY =
            ResetDays(
                resetType = ResetDaysType.WEEKLY,
                resetDays = 0
            )
        val MOTHLY =
            ResetDays(
                resetType = ResetDaysType.MONTHLY,
                resetDays = 0
            )
        val YEARLY =
            ResetDays(
                resetType = ResetDaysType.YEARLY,
                resetDays = 0
            )
        val EVERYOTHER =
            ResetDays(
                resetType = ResetDaysType.CUSTOM_DAYS,
                resetDays = 2
            )
        val THREE =
            ResetDays(
                resetType = ResetDaysType.CUSTOM_DAYS,
                resetDays = 2
            )
    }
}

enum class ResetDaysType(
    val uiText: Int
) {
    DAILY(uiText = R.string.DailyChange),
    WEEKLY(uiText = R.string.WeekklyChange),
    MONTHLY(uiText = R.string.MonthlyChange),
    YEARLY(uiText = R.string.YearlyChange),
    CUSTOM_DAYS(uiText = R.string.CustomDaysChange),
}

data class ResetTime(
    val hour: Int,
    val minute: Int
) {
    companion object {
        val MIDNIGHT = ResetTime(
            hour = 0,
            minute = 0
        )
        val MIDDAY = ResetTime(
            hour = 12,
            minute = 0
        )
        val LASTMINUTE = ResetTime(
            hour = 23,
            minute = 59
        )
    }
}

enum class checkTypes(
    val stringRef: Int
) {
    COMPLETIONS(stringRef = R.string.ChecksCompletions),
    STEPS(stringRef = R.string.ChecksSteps),
}

