package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.data.definitions.ui.AllHabitsViewModel
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.NavigationType
import com.averyvi.spiritfire.ui.Routes
import com.averyvi.spiritfire.ui.components.BigHabitPropertiesCard
import com.averyvi.spiritfire.ui.components.MinimalHabitPropertiesCard
import com.averyvi.spiritfire.ui.components.SmallHabitPropertiesCard

@Composable
fun AllHabitsScreen(
    navigateFunc: (NavigationType, Routes) -> Unit,
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository
){
    val AllHabitsVMfactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllHabitsViewModel(
                habitRepository = habitRepository,
                habitFilterViewModel = habitFilterViewModel,

            ) as T
        }
    }
    val AllHabitsViewModel: AllHabitsViewModel = viewModel(factory = AllHabitsVMfactory)
    val displayedHabits = AllHabitsViewModel.displayedHabits.collectAsState().value

    val firstGroupSize = (displayedHabits.size * 0.2).toInt()
    val secondGroupSize = (displayedHabits.size * 0.3).toInt()

    val showDetails = { it: Int ->
        habitFilterViewModel.changeShownDetail(it)
        navigateFunc(NavigationType.CUSTOM, Routes.DetailedHabitAnalytics)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(125.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = firstGroupSize,
            span = { GridItemSpan(3) },
        ) { viewPosition ->
            val habitRow = displayedHabits[viewPosition]

            BigHabitPropertiesCard(
                habit = habitRow,
                onCompleteClick = { showDetails(habitRow.id) },
            )
        }

        item( span = { GridItemSpan(maxLineSpan) } ) {
            Spacer(Modifier.height(16.dp))
        }

        items(
            count = secondGroupSize,
            span = { GridItemSpan(3) },
        ) { viewPosition ->
            val habitRow = displayedHabits[viewPosition + firstGroupSize]

            SmallHabitPropertiesCard(
                habit = habitRow,
                onCompleteClick = { showDetails(habitRow.id) },
            )
        }

        items(
            count = displayedHabits.size - firstGroupSize - secondGroupSize,
            span = { GridItemSpan(1) },
        ) { viewPosition ->
            val habitRow = displayedHabits[viewPosition + firstGroupSize + secondGroupSize]

            MinimalHabitPropertiesCard(
                habit = habitRow,
                onCompleteClick = { showDetails(habitRow.id) },
            )
        }

    }
}