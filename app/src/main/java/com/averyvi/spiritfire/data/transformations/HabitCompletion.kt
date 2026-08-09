package com.averyvi.spiritfire.data.transformations

import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow

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

fun contractPeriods(
    isDone: MutableList<Boolean>,
    graceGroup: MutableList<Boolean>,
    periodsToShow: periodsToShow,
):  MutableList<Boolean> {
    when(periodsToShow.type) { // todo make the logic configurable
        logDisplayLength.MONTHS -> { // combine into weeks
            val isDoneContracted = mutableListOf<Boolean>()
            var index = 0
            while (index < isDone.size) {
                var group = 0
                val maxPos = minOf(index + 7, isDone.size)
                for (logPos in index until maxPos) {
                    if (isDone[logPos]) group += 2
                    else if (graceGroup[logPos]) group++
                } // max value = 14 ; acceptable = 9
                isDoneContracted.add(group >= 9)
                index += 7 // Increment to advance loop
            }
            return isDoneContracted
        }
        logDisplayLength.YEARS -> { // combine into weeks
            val isDoneContracted = mutableListOf<Boolean>()
            var index = 0
            while (index < isDone.size) {
                var group = 0
                val maxPos = minOf(index + 7, isDone.size)
                for (logPos in index until maxPos) {
                    if (isDone[logPos]) group += 2
                    else if (graceGroup[logPos]) group++
                } // max value = 104 ; acceptable = 66
                isDoneContracted.add(group >= 66)
                index += 7 // Increment to advance loop
            }
            return isDoneContracted
        }
        else -> {
            return isDone
        }
    }
}