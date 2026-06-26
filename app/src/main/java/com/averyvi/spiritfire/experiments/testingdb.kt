package com.averyvi.spiritfire.experiments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.habits.checkTypes
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.components.BigHabitOverviewCard
import java.util.Date

@Composable
fun testingdb(
    habitRepository: HabitRepository,
    habitFilterViewModel: HabitFilterViewModel,
){/*
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
    }*/
    HabitTestingScreen(
        habitRepository = habitRepository
    )
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

fun generateRandomLogEntity(existingHabits: List<HabitRow>): HabitLogDBEntity {
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

//testing functions by an llm

@Composable
fun HabitTestingScreen(
    habitRepository: HabitRepository,
    modifier: Modifier = Modifier
) {
    // Collect all habits as a flow
    val habitsWithTags by habitRepository.getAllHabits().collectAsState(initial = emptyList())

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(habitsWithTags.size) { habitDbEntity ->
            // Convert to the UI state row using your existing extension function
            val habitRow = habitsWithTags[habitDbEntity]

            // Collect logs for this specific habit
            val logs by habitRepository.getAllLogsForHabit(habitRow.id).collectAsState(initial = emptyList())

            HabitTestingCard(
                habit = habitRow,
                logs = logs
            )
            // add my onw card
            BigHabitOverviewCard(
                habit = habitRow,
                completeCount = if(logs.isNotEmpty()) logs.sortedBy { it.logTime }.first().checks else 0,
            )
        }
    }
}

@Composable
fun HabitTestingCard(
    habit: HabitRow,
    logs: List<HabitLogItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // --- HEADER: Visual Icon, Color, and Name ---
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display the custom color mapped to the DB integer
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = habit.colour, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Display the dynamic icon based on the resource ID
                    Icon(
                        painter = painterResource(id = habit.icon),
                        contentDescription = habit.name,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (habit.description.isNotBlank()) {
                        Text(
                            text = habit.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- DETAILS: Settings & Checks ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Priority: ${habit.priority}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "Difficulty: ${habit.difficulty}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Checks: ${habit.checksComplete} / ${habit.checksAmount}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "Reset: ${habit.resetType.name} (Days: ${habit.resetDays})",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(12.dp))

            // --- LOG DATA ---
            Text(
                text = "Log Entries: ${logs.size}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            if (logs.isNotEmpty()) {
                val latestLog = logs.maxByOrNull { it.logTime }
                latestLog?.let {
                    Text(
                        text = "Latest log: ${Date(it.logTime)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Checks logged: ${it.checks}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Text(
                    text = "No logs yet for this habit.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}