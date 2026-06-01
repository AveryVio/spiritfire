package com.averyvi.spiritfire.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColor
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.ResetDays
import com.averyvi.spiritfire.data.definitions.habits.checkTypes
import java.util.Calendar
import kotlin.concurrent.thread

@Composable
fun testingdb(habitDAO: HabitRegistryUserDao){
    Button(
        onClick = {
            thread {
                habitDAO.insert(
                    HabitRegistryDBEntity(
                        name = "Elk",
                        description = "Antlers",
                        icon = R.drawable.r_outline_pill_24,
                        colour = FColour.Yellow.color.toArgb(),
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
        }
    ) { Text("one add") }

    Button(
        onClick = {
            thread {
                habitDAO.insert(
                    HabitRegistryDBEntity(
                        name = "Frog",
                        description = "Croaks",
                        icon = R.drawable.r_outline_battery_android_0_24,
                        colour = FColour.Green.color.toArgb(),
                        resetType = ResetDays.YEARLY.resetType,
                        resetDays = ResetDays.YEARLY.resetDays,
                        resetHour = if ((Calendar.HOUR_OF_DAY + 1) <= 24) Calendar.HOUR_OF_DAY + 1 else 24,
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
        }
    ) { Text("two add") }
    val allHabits: SnapshotStateList<HabitRegistryDBEntity> = SnapshotStateList()
    Button(
        onClick = {
            thread {
                habitDAO.getAll().forEach { allHabits.add(it) }
            }.join()
        }
    ) { Text("getall") }
    Button(
        onClick = {
            thread {
                habitDAO.getAll().forEach { allHabits.add(it) }
                allHabits.forEach { habitDAO.delete(it) }
            }.join()
        }
    ) { Text("deletall") }
    LazyColumn(userScrollEnabled = true) {
        allHabits.forEach {
            item {
                Column() {
                    Text("${it.id}")
                    Text(it.name + ", " + it.description)
                    Text("${it.icon}")
                    Text("${it.colour.toColor()}")
                    Text("${it.resetType}, ${it.resetDays}, ${it.resetHour}, ${it.resetMinute}, ${it.resetOffset}")
                    Text("${it.checksAmount}, ${it.checksComplete}")
                    Text("${it.checksType}, ${it.checksSkipGrace}, ${it.checksNames}")
                    Text("${it.priority}")
                    Text("${it.cooldownHours}, ${it.cooldownMinutes}, ${it.cooldownSeconds}")
                    Text("${it.difficulty}")
                    Text("\n\n\n\n")
                }
            }
        }
    }
}