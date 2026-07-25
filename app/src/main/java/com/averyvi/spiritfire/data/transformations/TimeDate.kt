package com.averyvi.spiritfire.data.transformations

import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

fun isWithinPeriod(
    habitRow: HabitRow,
    targetTimestamp: Long,
    periodsAgo: Long
): Boolean {
    val localZone = ZoneId.systemDefault()
    val now = ZonedDateTime.now(localZone)
    val actionTime = Instant.ofEpochMilli(targetTimestamp).atZone(localZone)


    var periodStart = getStartingPeriod(
        now = now, // time of reset within a single day
        resetHour = habitRow.resetHour,
        resetMinute = habitRow.resetMinute,
    )

    /*
    general guide:
        find the latest day that it was supposed to reset
        adjust period for reseting
        get the end for the period
        figure out if the action time is within the period
     */

    periodStart = getAdjustedPeriod(
        now = now,
        periodStart = periodStart,
        localZone = localZone,
        habitRow = habitRow,
        periodsAgo = periodsAgo,
    )

    val periodEnd = getEndPeriod(
        periodStart = periodStart,
        resetType = habitRow.resetType,
        resetDays = habitRow.resetDays.toLong()
    )

    return (
            actionTime.isEqual(periodStart)
                    || actionTime.isAfter(periodStart)
                    && actionTime.isBefore(periodEnd)
            )
}

fun getStartingPeriod(
    now: ZonedDateTime,
    resetHour: Int,
    resetMinute: Int,
): ZonedDateTime {
    return now
        .withHour(resetHour)
        .withMinute(resetMinute)
        .withSecond(0)
        .withNano(0)
}

fun getAdjustedPeriod(
    now: ZonedDateTime,
    periodStart: ZonedDateTime,
    localZone: ZoneId,
    habitRow: HabitRow,
    periodsAgo: Long
): ZonedDateTime {
    var periodStart = periodStart

    when(habitRow.resetType) {
        ResetDaysType.DAILY -> {
            if (now.isBefore(periodStart)) {
                periodStart = periodStart.minusDays(1)
            }
            periodStart.minusDays(periodsAgo)
        }

        ResetDaysType.WEEKLY -> {
            // Assuming habitRow.resetOffset represents DayOfWeek (1 = Monday, 7 = Sunday)

            val resetDayOfWeek = DayOfWeek.of(habitRow.resetOffset)
            periodStart = periodStart.with(TemporalAdjusters.previousOrSame(resetDayOfWeek))

            if (now.isBefore(periodStart)) {
                periodStart = periodStart.minusWeeks(1)
            }
            periodStart = periodStart.minusWeeks(periodsAgo)
        }

        ResetDaysType.MONTHLY -> {
            // Assuming resetOffset represents the day of the month (1-31)

            val maxDaysInMonth = now.month.length(now.toLocalDate().isLeapYear)
            val safeOffset = minOf(
                habitRow.resetOffset,
                maxDaysInMonth
            ) // if offset is 31 but month has 30 days, it snaps to 30.

            periodStart = periodStart.withDayOfMonth(safeOffset)

            if (now.isBefore(periodStart)) {
                periodStart = periodStart.minusMonths(1)

                // Re-calculate safe day for the previous month
                val prevMonthSafe = periodStart.month.length(periodStart.toLocalDate().isLeapYear)
                periodStart = periodStart.withDayOfMonth(minOf(habitRow.resetOffset, prevMonthSafe))
            }
            periodStart = periodStart.minusMonths(periodsAgo)
        }

        ResetDaysType.YEARLY -> {
            // Assuming resetOffset is Day of Year (1-365)

            periodStart = periodStart.withDayOfYear(habitRow.resetOffset)

            if (now.isBefore(periodStart)) {
                periodStart = periodStart.minusYears(1)
            }

            periodStart = periodStart.minusYears(periodsAgo)
        }

        ResetDaysType.CUSTOM_DAYS -> {
            // Assuming resetOffset is within resetDays range
            val anchorStart = Instant.EPOCH.atZone(localZone)
                .withHour(habitRow.resetHour)
                .withMinute(habitRow.resetMinute)
                .withSecond(0)
                .withNano(0)

            val effectiveAnchor = anchorStart.plusDays(habitRow.resetOffset.toLong())
            val daysSinceEpoch = ChronoUnit.DAYS.between(effectiveAnchor, now)

            val periodsElapsed = Math.floorDiv(daysSinceEpoch, habitRow.resetDays)
            val targetPeriodIndex = periodsElapsed - periodsAgo

            val totalDaysToAdd = targetPeriodIndex * habitRow.resetDays
            periodStart = effectiveAnchor.plusDays(totalDaysToAdd)
        }
    }
    return periodStart
}

fun getEndPeriod(
    periodStart: ZonedDateTime,
    resetType: ResetDaysType,
    resetDays: Long
): ZonedDateTime {
 return when (resetType) {
     ResetDaysType.DAILY -> periodStart.plusDays(1)
     ResetDaysType.WEEKLY -> periodStart.plusWeeks(1)
     ResetDaysType.MONTHLY -> periodStart.plusMonths(1)
     ResetDaysType.YEARLY -> periodStart.plusYears(1)
     ResetDaysType.CUSTOM_DAYS -> periodStart.plusDays(resetDays)
 }
}