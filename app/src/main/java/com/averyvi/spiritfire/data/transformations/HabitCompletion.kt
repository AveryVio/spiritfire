package com.averyvi.spiritfire.data.transformations

import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.ui.basic.logDisplayLength
import com.averyvi.spiritfire.ui.basic.periodsToShow
import java.time.ZoneId
import java.time.ZonedDateTime

fun getCompletedPeriods(
    beginingOfPeriods: Int,
    shownItems: Int,
    habitRow: HabitRow,
    logsList: List<HabitLogItem>
): MutableList<Boolean> {
    if (logsList.isEmpty()) return MutableList(shownItems) { false }

    val completedPeriods = mutableListOf<Boolean>()
    val localZone = ZoneId.systemDefault()
    val now = ZonedDateTime.now(localZone)
    val basePeriodStart = getStartingPeriod(now, habitRow.resetHour, habitRow.resetMinute)

    for (period in (beginingOfPeriods)..<(beginingOfPeriods + shownItems)) {
        val periodStart = getAdjustedPeriod(now, basePeriodStart, localZone, habitRow, period.toLong())
        val periodEnd = getEndPeriod(periodStart, habitRow.resetType, habitRow.resetDays.toLong())

        val startMillis = periodStart.toInstant().toEpochMilli()
        val endMillis = periodEnd.toInstant().toEpochMilli()

        var totalChecks = 0
        for (log in logsList) {
            if (log.logTime in startMillis until endMillis) {
                totalChecks += log.checks
            }
        }

        val requiredChecks = if (habitRow.checksComplete > 0) habitRow.checksComplete else 1
        completedPeriods.add(totalChecks >= requiredChecks)
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
        if (!completionArray[newerPeriods]) {
            misses++
            if (misses > skipGrace) break
        } else {
            foundFutureAnchor = true
            break
        }
    }

    var olderPeriods = completionArrayIndex + 1
    while (olderPeriods < completionArray.size) {
        if (!completionArray[olderPeriods]) {
            misses++
            if (misses > skipGrace) break
        } else {
            foundPastAnchor = true
            break
        }
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