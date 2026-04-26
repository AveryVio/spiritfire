package com.averyvi.spiritfire.data.definitions

import com.averyvi.spiritfire.R
import java.util.Date

data class ResetTime(
    val hour: Int,
    val minute: Int
) {
    companion object {
        val MIDNIGHT = ResetTime(hour = 0, minute = 0)
        val MIDDAY = ResetTime(hour = 12, minute = 0)
        val LASTMINUTE = ResetTime(hour = 23, minute = 59)
    }
}

data class ResetDaysInterval(
    val interval_unit: ResetDaysIntervalUnit,
    val interval_value: String,
) {
    companion object {
        val DAILY = ResetDaysInterval(interval_unit = ResetDaysIntervalUnit.DAILY, interval_value = "")
        val WEEKLY = ResetDaysInterval(interval_unit = ResetDaysIntervalUnit.WEEKLY, interval_value = "")
        val MOTHLY = ResetDaysInterval(interval_unit = ResetDaysIntervalUnit.MONTHLY, interval_value = "")
        val YEARLY = ResetDaysInterval(interval_unit = ResetDaysIntervalUnit.YEARLY, interval_value = "")
        val EVERYOTHER = ResetDaysInterval(interval_unit = ResetDaysIntervalUnit.CUSTOM_DAYS, interval_value = "2")
        val THREE = ResetDaysInterval(interval_unit = ResetDaysIntervalUnit.CUSTOM_DAYS, interval_value = "3")
    }
}

enum class ResetDaysIntervalUnit(
    val uiText: Int
) {
    DAILY(uiText = R.string.DailyChange),
    WEEKLY(uiText = R.string.WeekklyChange),
    MONTHLY(uiText = R.string.MonthlyChange),
    YEARLY(uiText = R.string.YearlyChange),
    CUSTOM_DAYS(uiText = R.string.CustomDaysChange),
}