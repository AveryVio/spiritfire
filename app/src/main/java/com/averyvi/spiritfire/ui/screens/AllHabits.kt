package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.data.definitions.ui.AllHabitsViewModel
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.components.BigHabitPropertiesCard
import com.averyvi.spiritfire.ui.components.MinimalHabitPropertiesCard
import com.averyvi.spiritfire.ui.components.SmallHabitPropertiesCard

@Composable
fun AllHaibitsScreen(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository
){
    val AllHabitsVMfactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllHabitsViewModel(
                habitRepository = habitRepository,
                habitFilterViewModel = habitFilterViewModel
            ) as T
        }
    }
    val AllHabitsViewModel: AllHabitsViewModel = viewModel(factory = AllHabitsVMfactory)
    val displayedHabits = AllHabitsViewModel.displayedHabits.collectAsState().value

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(displayedHabits.size) { viewPosition ->
            val habitRow = displayedHabits[viewPosition]

            BigHabitPropertiesCard(
                habit = habitRow,
                onCompleteClick = { },
            )
            SmallHabitPropertiesCard(
                habit = habitRow,
                onCompleteClick = { },
            )
            MinimalHabitPropertiesCard(
                habit = habitRow,
                onCompleteClick = { },
            )
        }

    }
}