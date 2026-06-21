package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.sources.db.HabitLogUserDao
import com.averyvi.spiritfire.data.sources.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.definitions.ui.OverviewViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.basic.CircularHabitProgress
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.basic.LinearIconifiedProgress
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

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Column(
                modifier = Modifier.padding(16.dp + 8.dp)
            ) {
                UICard() {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularHabitProgress(
                                    icon = R.drawable.r_outline_dark_mode_2,
                                    colour = FColour.Purple.color,
                                    complete = true,
                                    progress = 0.85f,
                                    size = 32.dp
                                )
                                Text(
                                    text = "Habit",
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "jfkldsjfl"
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp + 2.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LinearIconifiedProgress(
                                iconCount = 5,
                                icon = R.drawable.ur_mode_heat_24dp_000000_fill0_wght400_grad0_opsz24,
                                value = 4,
                                colour = FColour.Red.color,
                                size = 16.dp + 8.dp
                            )
                            LinearIconifiedProgress(
                                iconCount = 7,
                                icon = R.drawable.ur_star_24dp_000000_fill0_wght400_grad0_opsz24,
                                value = 4,
                                colour = FColour.Yellow.color,
                                size = 16.dp + 8.dp
                            )
                        }
                        Spacer(Modifier.height(4.dp + 2.dp))
                        HorizontalDivider(
                            thickness = 2.dp
                        )
                        Spacer(Modifier.height(4.dp + 2.dp))
                        Row() {
                            // streak
                            // last few checks
                        }
                    }
                }
            }
        }

    }
}

/**
 * Completions screen
 * // completed today + streaks(card)
 * // this month (group of cards)
 * // completion ratio this year
 */