package com.averyvi.spiritfire.experiments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.HabitTagCrossRef
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.data.transformations.isWithinPeriod
import com.averyvi.spiritfire.ui.appUI.top.GeneralAppBar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun testingScreenUI(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository,
    outerPadding: PaddingValues
) {
    Scaffold(
        modifier = Modifier.padding(outerPadding),
        topBar = {
            GeneralAppBar()
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("fjsklfdj")
            Button(onClick = {
                habitFilterViewModel.viewModelScope.launch {
                    habitRepository.insertHabit(
                        generateRandomHabitEntity()
                    )
                }
            }) { Text("add registry") }
            Button(onClick = {
                habitFilterViewModel.viewModelScope.launch {
                    val newValue: HabitLogDBEntity = generateRandomLogEntity(
                        listOf(
                            HabitRow(tags = emptyList())
                        )
                    )
                    habitRepository.insertLog(
                        generateRandomLogEntity(
                            habitRepository.getAllHabits().first()
                        )
                    )
                }
            }) { Text("add log") }
            Button(onClick = {
                habitFilterViewModel.viewModelScope.launch {
                    val habits = habitRepository.getAllHabits().first()
                    val randomPeriod = (0..7L).random() // Adjust range as needed

                    val newLog = generateRandomLogWithinPeriod(habits, randomPeriod)
                    if (newLog != null) {
                        habitRepository.insertLog(newLog)
                    }
                }
            }) { Text("add period-specific log") }
            Button(onClick = {
                habitFilterViewModel.viewModelScope.launch {
                    val habits = habitRepository.getAllHabits().first()

                    if (habits.isNotEmpty()) {
                        // 1. Pick a random habit
                        val selectedHabit = habits.random()

                        // 2. Fetch the existing logs specifically for this habit from the repository
                        val existingLogs =
                            habitRepository.getAllLogsForHabit(selectedHabit.id).first()

                        // 3. Check if any log already exists in the current period (periodsAgo = 0)
                        val alreadyLoggedThisPeriod = existingLogs.any { log ->
                            isWithinPeriod(
                                habitRow = selectedHabit,
                                targetTimestamp = log.logTime,
                                periodsAgo = 0L
                            )
                        }

                        // 4. Only insert a new log if the current period is empty
                        if (!alreadyLoggedThisPeriod) {
                            val maxChecks = selectedHabit.checksAmount
                            val newLog = HabitLogDBEntity(
                                logTime = System.currentTimeMillis(),
                                checks = if (maxChecks > 0) maxChecks else 0,
                                habit = selectedHabit.id
                            )
                            habitRepository.insertLog(newLog)
                        }
                    }
                }
            }) { Text("add log now (if empty)") }
            Button(onClick = {
                habitFilterViewModel.viewModelScope.launch {
                    habitRepository.getAllHabitEntities().first()
                        .forEach { habitRepository.deleteHabit(it) }
                }
            }) { Text("remove") }
            Button(onClick = {
                habitFilterViewModel.viewModelScope.launch {
                    habitRepository.insertTag(
                        generateRandomTagEntity()
                    )
                }
            }) { Text("add random tag") }

            Button(onClick = {
                habitFilterViewModel.viewModelScope.launch {
                    val habits = habitRepository.getAllHabits().first()
                    val tags = habitRepository.getAllTags().first()

                    if (habits.isNotEmpty() && tags.isNotEmpty()) {
                        val randomHabit = habits.random()
                        val randomTag = tags.random()

                        habitRepository.insertCrossRef(
                            HabitTagCrossRef(
                                habitId = randomHabit.id,
                                tagId = randomTag.id
                            )
                        )
                    }
                }
            }) { Text("link random tag to habit") }

            testingdb(
                habitFilterViewModel = habitFilterViewModel,
                habitRepository = habitRepository,
            )
        }
    }
}