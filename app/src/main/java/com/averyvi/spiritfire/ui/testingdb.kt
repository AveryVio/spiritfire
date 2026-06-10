package com.averyvi.spiritfire.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toString
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColor
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.db.HabitLogUserDao
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.ResetDays
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.habits.checkTypes
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import java.util.Calendar
import java.util.Date
import kotlin.collections.forEach
import kotlin.concurrent.thread

@Composable
fun testingdb(
    habitDAO: HabitRegistryUserDao,
    logDAO: HabitLogUserDao,
    habitFilterViewModel: HabitFilterViewModel,
){
    val allHabits = habitFilterViewModel.filterItems.collectAsState().value
    val selectedHabits = habitFilterViewModel.selectedFilters.collectAsState().value

    var logvalues: List<HabitLogDBEntity> = emptyList()
    thread {
        logvalues = logDAO.getAll()
    }.join()

    LazyColumn(userScrollEnabled = true) {
        allHabits.forEach {
            item {
                Column() {
                    Text("${it.id}")
                    Text(it.name)
                    Text("${it.icon}")
                    Text("${it.colour.toColor()}")
                    /*
                    Text("${it.id}")
                    Text(it.name + ", " + it.description)
                    Text("${it.icon}")
                    Text("${it.colour.toColor()}")
                    Text("${it.resetType}, ${it.resetDays}, ${it.resetHour}, ${it.resetMinute}, ${it.resetOffset}")
                    Text("${it.checksAmount}, ${it.checksComplete}")
                    Text("${it.checksType}, ${it.checksSkipGrace}, ${it.checksNames}")
                    Text("${it.priority}")
                    Text("${it.cooldownHours}, ${it.cooldownMinutes}, ${it.cooldownSeconds}")
                    Text("${it.difficulty}")
                    Text("\n\n\n\n")
                    */
                }
            }
        }
        logvalues.forEach {
            item {
                Text(Date(it.logTime).toString())
                Text(it.habit.toString())
                Text(it.checks.toString())
            }
        }
    }
}

fun generateRandomHabitEntity(): HabitRegistryDBEntity {
    // Hardcoded lists for randomized resource/string generation
    val icons = listOf(
        R.drawable.r_outline_dark_mode_2,
        R.drawable.r_outline_battery_android_0_24,
        R.drawable.r_outline_deceased_24,
        R.drawable.r_outline_humidity_low_24,
        R.drawable.r_outline_jamboard_kiosk_24,
        R.drawable.r_outline_music_note_24,
        R.drawable.r_outline_pill_24
    )
    val names = listOf("Reading", "Exercise", "Meditation", "Coding", "Hydration", "Journaling", "Walking")
    val descriptions = listOf("Daily task", "Keep the streak going!", "Just do your best", "Don't forget this", "")

    // Restrictions for non-resource integer values
    val restrictedChecksAmount = (1..10).random()
    val restrictedChecksComplete = (0..restrictedChecksAmount).random() // Ensures completions don't exceed the total amount
    val restrictedPriority = (1..10).random()
    val restrictedDifficulty = (1..5).random()
    val restrictedCooldownHours = (0..24).random()
    val restrictedSkipGrace = (0..3).random()

    return HabitRegistryDBEntity(
        id = 0, // Kept at 0 so Room auto-generates the key
        name = names.random(),
        description = descriptions.random(),
        icon = icons.random(),
        colour = FColour.entries.random().color.toArgb(),
        resetType = ResetDaysType.entries.random(),

        // Time & Date integer restrictions
        resetDays = (1..30).random(),
        resetHour = (0..23).random(),
        resetMinute = (0..59).random(),
        resetOffset = (-12..12).random(),

        // Checks restrictions
        checksAmount = restrictedChecksAmount,
        checksComplete = restrictedChecksComplete,
        checksType = checkTypes.entries.random().stringRef,
        checksSkipGrace = restrictedSkipGrace,
        checksNames = "",

        // Settings restrictions
        priority = restrictedPriority,
        difficulty = restrictedDifficulty,

        // Cooldown restrictions
        cooldownHours = restrictedCooldownHours,
        cooldownMinutes = (0..59).random(),
        cooldownSeconds = (0..59).random()
    )
}

fun generateRandomLogEntity(existingHabits: List<HabitRegistryDBEntity>): HabitLogDBEntity {
    // Fallback if the registry is empty
    if (existingHabits.isEmpty()) {
        return HabitLogDBEntity(
            logTime = System.currentTimeMillis(),
            checks = 0,
            habit = 0 // Default ID if no habit exists
        )
    }

    // Select a random habit from the database
    val selectedHabit = existingHabits.random()

    // Generate a random date (e.g., a random time within the past 365 days)
    val oneDayInMillis = 24L * 60 * 60 * 1000
    val randomDaysAgo = (0..5475).random()
    val randomTime = System.currentTimeMillis() - (randomDaysAgo * oneDayInMillis)

    // Ensure the checks are within the range set by the habit's checksAmount
    val maxChecks = selectedHabit.checksAmount
    val randomChecks = if (maxChecks > 0) {
        (0..maxChecks).random()
    } else {
        0
    }

    return HabitLogDBEntity(
        logTime = randomTime,
        checks = randomChecks,
        habit = selectedHabit.id
    )
}