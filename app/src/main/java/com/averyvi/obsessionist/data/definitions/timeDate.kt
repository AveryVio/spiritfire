package com.averyvi.spiritfire.data.definitions

import com.averyvi.spiritfire.R
import java.util.Date

data class ResetTime(
    val hour: Int,
    val minute: Int
)

data class ResetDaysInterval(
    val interval_unit: ResetDaysIntervalUnit,
    val interval_value: Int,
    val interval_date_created: Date
)

enum class ResetDaysIntervalUnit(
    val uiText: Int
) {
    DAILY(uiText = R.string.DailyChange),
    WEEKLY(uiText = R.string.WeekklyChange),
    MONTHLY(uiText = R.string.MonthlyChange),
    YEARLY(uiText = R.string.YearlyChange),
    CUSTOM_DAYS(uiText = R.string.CustomDaysChange),
}