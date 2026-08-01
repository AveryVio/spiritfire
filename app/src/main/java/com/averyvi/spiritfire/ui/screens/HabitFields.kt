package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.ui.HabitFieldsViewModel
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.basic.LinearIconifiedProgress

@Composable
fun HabitFields(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository
) {
    val habitFieldsVMFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitFieldsViewModel(
                habitRepository = habitRepository,
                habitFilterViewModel = habitFilterViewModel,
            ) as T
        }
    }
    val habitFieldsViewModel: HabitFieldsViewModel = viewModel(factory = habitFieldsVMFactory)
    val habitRow = habitFieldsViewModel.habit.collectAsState().value

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp + 4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        ) {
            Icon(
                painter = painterResource(habitRow.icon),
                modifier = Modifier.size(48.dp),
                tint = habitRow.colour,
                contentDescription = null,
            )
            Column() {
                Text(
                    text = habitRow.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            // todo edit button
        }
        // todo completions and steps type
        Box(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = habitRow.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Box(
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.ResetAt),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val resetTime = listOf(
                    habitRow.resetHour,
                    habitRow.resetMinute
                )
                resetTime.forEachIndexed { index, value ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Card() {
                            Text(
                                text = value.toString(),
                                style = MaterialTheme.typography.displayLarge,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    if(index < ( resetTime.size - 1)) {
                        Text(
                            text = ":",
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            LinearIconifiedProgress(
                7, R.drawable.ur_star_24dp_000000_fill0_wght400_grad0_opsz24, habitRow.priority, MaterialTheme.colorScheme.primary, 25.dp
            )
            LinearIconifiedProgress(
                5, R.drawable.ur_mode_heat_24dp_000000_fill0_wght400_grad0_opsz24, habitRow.difficulty, MaterialTheme.colorScheme.secondary, 25.dp
            )
        }
    }
}