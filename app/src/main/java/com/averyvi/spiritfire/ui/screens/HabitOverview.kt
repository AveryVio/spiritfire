package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.data.sources.db.HabitLogUserDao
import com.averyvi.spiritfire.data.sources.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.definitions.ui.OverviewViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.components.UICard
import com.averyvi.spiritfire.ui.testingdb

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

    // repositories: viz Gemini

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // completed today + streaks(card)
        item {
            UICard() {
            }
        }
        // this month (group of cards)
        // completion ratio this year
    }

    testingdb(
        habitRepository = habitRepository,
        habitFilterViewModel = habitFilterViewModel
    )
}