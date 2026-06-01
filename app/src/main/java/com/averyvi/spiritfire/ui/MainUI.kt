package com.averyvi.spiritfire.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.ResetDays
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.habits.checkTypes
import java.util.Calendar

@Composable
fun MainUI(
    habitDAO: HabitRegistryUserDao
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Button(
                onClick = {
                    habitDAO.insert(
                        HabitRegistryDBEntity(
                            name = "Elk",
                            description = "Antlers",
                            icon = R.drawable.r_outline_pill_24,
                            colour = FColour.Yellow.color,
                            resetType = ResetDays.WEEKLY.resetType,
                            resetDays = ResetDays.WEEKLY.resetDays,
                            resetHour = Calendar.HOUR_OF_DAY,
                            resetMinute = Calendar.MINUTE,
                            resetOffset = 0,
                            checksAmount = 3,
                            checksComplete = 2,
                            checksType = checkTypes.COMPLETIONS.stringRef,
                            checksSkipGrace = 1,
                            checksNames = "",
                            priority = 2,
                            cooldownHours = 0,
                            cooldownMinutes = 2,
                            cooldownSeconds = 0,
                            difficulty = 7,
                        )
                    )
                }
            ) { Text("one add") }

            Button(
                onClick = {
                    habitDAO.insert(
                        HabitRegistryDBEntity(
                            name = "Frog",
                            description = "Croaks",
                            icon = R.drawable.r_outline_battery_android_0_24,
                            colour = FColour.Green.color,
                            resetType = ResetDays.YEARLY.resetType,
                            resetDays = ResetDays.YEARLY.resetDays,
                            resetHour = if((Calendar.HOUR_OF_DAY + 1) <= 24) Calendar.HOUR_OF_DAY + 1 else 24 ,
                            resetMinute = Calendar.MINUTE,
                            resetOffset = 0,
                            checksAmount = 1,
                            checksComplete = 1,
                            checksType = checkTypes.STEPS.stringRef,
                            checksSkipGrace = 0,
                            checksNames = "",
                            priority = 7,
                            cooldownHours = 1,
                            cooldownMinutes = 0,
                            cooldownSeconds = 0,
                            difficulty = 1,
                        )
                    )
                }
            ) { Text("two add") }
        }
    }
}