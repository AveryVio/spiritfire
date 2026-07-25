package com.averyvi.spiritfire.ui.basic

import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType

enum class logDisplayLength(
    val string1: Int,
    val string2Singular: Int,
    val string2Plural: Int
) {
    DAYS(
        string1 = R.string.DailyChange,
        string2Singular = R.string.DailyChange2Singular,
        string2Plural = R.string.DailyChange2Plural,
    ),
    WEEKS(
        string1 = R.string.WeekklyChange,
        string2Singular = R.string.WeeklyChange2Singular,
        string2Plural = R.string.WeeklyChange2Plural,
    ),
    MONTHS(
        string1 = R.string.MonthlyChange,
        string2Singular = R.string.MonthlyChange2Singular,
        string2Plural = R.string.MonthlyChange2Plural,
    ),
    YEARS(
        string1 = R.string.YearlyChange,
        string2Singular = R.string.YearlyChange2Singular,
        string2Plural = R.string.YearlyChange2Plural,
    )
}

enum class periodsToShow(
    val type: logDisplayLength,
    val amount: Int,
) {
    FIVE_DAYS(
        type = logDisplayLength.DAYS,
        amount = 5,
    ),
    WEEK(
        type = logDisplayLength.WEEKS,
        amount = 1,
    ),
    TWO_WEEKS(
        type = logDisplayLength.WEEKS,
        amount = 2,
    ),
    MONTH(
        type = logDisplayLength.MONTHS,
        amount = 1,
    ),
    TWO_MONTHS(
        type = logDisplayLength.MONTHS,
        amount = 2,
    ),
    YEAR(
        type = logDisplayLength.YEARS,
        amount = 1,
    ),
}