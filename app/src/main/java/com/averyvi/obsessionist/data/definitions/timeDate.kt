package com.averyvi.obsessionist.data.definitions

data class ResetTime(
    val hour: Int,
    val minute: Int
)

enum class ResetDaysInterval(
    val days: Int
) {
    daily(days = 1),
    everyOther(days = 2),
    weekly(days = 7)
}