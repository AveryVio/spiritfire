package com.averyvi.spiritfire.data.transformations

import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow

fun getCompletedPeriods(
    beginingOfPeriods: Int,
    shownItems: Int,
    habitRow: HabitRow,
    logsList: List<HabitLogItem>
): MutableList<Boolean> {
    var completedPeriods: MutableList<Boolean> = mutableListOf()

    for (period in (beginingOfPeriods)..<(beginingOfPeriods + shownItems)) {
        val logsInPeriod = logsList.filter { log ->
            isWithinPeriod(
                habitRow = habitRow,
                targetTimestamp = log.logTime,
                periodsAgo = period.toLong(),
            )
        }
        val totalChecks = logsInPeriod.sumOf { it.checks }

        val requiredChecks = if (habitRow.checksAmount > 0) habitRow.checksAmount else 1

        completedPeriods.add(period, totalChecks >= requiredChecks)
    }

    return completedPeriods
}

fun determineGrace(
    completionArrayIndex: Int,
    completionArray: MutableList<Boolean>,
    skipGrace: Int,
): Boolean {

    var misses = 1
    var foundPastAnchor = false
    var foundFutureAnchor = false

    var newerPeriods = completionArrayIndex - 1
    while (newerPeriods >= 0) {
        if (!completionArray[newerPeriods]) misses++
        else {
            foundFutureAnchor = true
            break
        }
        newerPeriods--
    }

    var olderPeriods = completionArrayIndex + 1
    while (olderPeriods < completionArray.size) {
        if (!completionArray[olderPeriods]) misses++
        else {
            foundPastAnchor = true
            break
        }
        olderPeriods++
    }
    return (misses <= skipGrace) && foundPastAnchor && foundFutureAnchor
}