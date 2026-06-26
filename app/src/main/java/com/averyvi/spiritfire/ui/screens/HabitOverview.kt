package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.definitions.ui.OverviewViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.components.WideHabitOverviewCard
import androidx.compose.runtime.collectAsState

@Composable
fun HabitOverview(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository
){
    val OverviewVMfactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OverviewViewModel(
                habitRepository = habitRepository,
                habitFilterViewModel = habitFilterViewModel
            ) as T
        }
    }
    val OverviewViewModel: OverviewViewModel = viewModel(factory = OverviewVMfactory)
    val displayedHabits = OverviewViewModel.displayedHabits.collectAsState().value
    val filtredLogs = OverviewViewModel.filtredLogs.collectAsState().value

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(displayedHabits.size) { viewPosition ->
            val habitRow = displayedHabits[viewPosition]
            val logsList = filtredLogs.filter { it.habit == habitRow.id }

            WideHabitOverviewCard(
                habit = habitRow,
                onCompleteClick = { },
                completeCount = if(logsList.isNotEmpty()) logsList.first().checks else 0, // test and then implement the currently complete logic
            )
        }

    }
}

/**
 * Completions screen
 * // completed today + streaks(card)
 * // this month (group of cards)
 * // completion ratio this year
 */