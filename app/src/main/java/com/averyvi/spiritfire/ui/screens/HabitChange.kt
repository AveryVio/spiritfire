package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.definitions.ui.HabitChangeViewModel
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.definitions.ui.OverviewViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.appUI.top.EditingAppBar
import com.averyvi.spiritfire.ui.appUI.top.GeneralAppBar
import com.averyvi.spiritfire.ui.basic.FancyTextInput
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.ui.basic.SimpleTextInput

@Composable
fun HabitChange(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository,
    outerPadding: PaddingValues
) {
    val HabitChangeVMfactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitChangeViewModel(
                habitRepository = habitRepository,
                habitFilterViewModel = habitFilterViewModel,
                changingHabit = HabitRow.NO_HABIT
            ) as T
        }
    }
    val HabitChangeViewModel: HabitChangeViewModel = viewModel(factory = HabitChangeVMfactory)

    val editedHabit = HabitChangeViewModel.editedHabit.collectAsState().value

    val variousContentScroll = rememberScrollState()

    Scaffold(
        modifier = Modifier.padding(outerPadding),
        topBar = {
            EditingAppBar(
                HabitChangeViewModel.editedHabit.collectAsState().value
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(8.dp).padding(innerPadding)
                .verticalScroll(variousContentScroll),
            verticalArrangement = Arrangement.spacedBy(4.dp),

        ) {
            FancyTextInput(
                label = @Composable { Text(stringResource(R.string.HabitName)) },
                placeholder = @Composable { Text(stringResource(R.string.placeholdertext)) },
                value = editedHabit.name,
                onValueChange = {HabitChangeViewModel.changeValue(name = it)},
                brushColorList = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary),
                gradientTextStyle = TextStyle.Default.copy(
                    fontWeight = FontWeight.Bold
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            SimpleTextInput(
                label = @Composable { Text(stringResource(R.string.HabitDesc)) },
                placeholder = @Composable { Text(stringResource(R.string.placeholdertext)) },
                value = editedHabit.description,
                onValueChange = {HabitChangeViewModel.changeValue(description = it)},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
            }
            // colour
            // icon
            // checks
            // reset
            // grace
            // cooldown
            // priority, difficulty
            // tags
        }
    }
}