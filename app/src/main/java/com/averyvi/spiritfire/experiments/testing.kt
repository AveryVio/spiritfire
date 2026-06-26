package com.averyvi.spiritfire.experiments

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun testingScreenUI(

    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository
){
    Column() {
        Text("fjsklfdj")
        Text("fjsklfdj")/*
        Button(onClick = {
            expandBottomSheet(scaffoldState, scope)
        }) { Text("exp load") }*/
        Button(onClick = {
            habitFilterViewModel.viewModelScope.launch {
                habitRepository.insertHabit(
                    generateRandomHabitEntity()
                )
            }
        }) { Text("add registry") }
        Button(onClick = {
            habitFilterViewModel.viewModelScope.launch {
                var newValue: HabitLogDBEntity = generateRandomLogEntity(
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
                habitRepository.getAllHabitEntities().first().forEach { habitRepository.deleteHabit(it) }
            }
        }) { Text("remove") }
        testingdb(
            habitFilterViewModel = habitFilterViewModel,
            habitRepository = habitRepository,
        )
    }
}