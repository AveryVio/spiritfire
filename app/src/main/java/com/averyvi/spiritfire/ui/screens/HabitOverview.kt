package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.definitions.ui.OverviewViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import androidx.compose.runtime.collectAsState
import com.averyvi.spiritfire.data.definitions.sortingfiltering.ColumnType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.FilteringType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering.Companion.addFilter
import com.averyvi.spiritfire.data.definitions.sortingfiltering.SortingFiltering.Companion.addSorting
import com.averyvi.spiritfire.ui.basic.FlowPillButton
import kotlinx.coroutines.flow.Flow

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
                habitFilterViewModel = habitFilterViewModel,
                sortingFiltering = SortingFiltering.TESTING // todo: temp
            ) as T
        }
    }
    val OverviewViewModel: OverviewViewModel = viewModel(factory = OverviewVMfactory)
    val displayedHabits = OverviewViewModel.displayedHabits.collectAsState().value
    val filtredLogs = OverviewViewModel.filtredLogs.collectAsState().value
    val sortingFiltering = OverviewViewModel.sortingFiltering

    Column() {
        FlowRow() {
            FlowPillButton(
            ) {

            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(displayedHabits.size) { viewPosition ->
            val habitRow = displayedHabits[viewPosition]
            val logsList = filtredLogs.filter { it.habit == habitRow.id }
        }

    }
}

/**
 * Completions screen
 * // completed today + streaks(card)
 * // this month (group of cards)
 * // completion ratio this year
 */