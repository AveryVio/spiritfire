package com.averyvi.spiritfire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.averyvi.spiritfire.old.ui.theme.SpiritfireTheme
import com.averyvi.spiritfire.ui.MainUI
import com.averyvi.spiritfire.data.db.HabitRegistry

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val habitDatabase = Room.databaseBuilder(
            context = applicationContext,
            klass = HabitRegistry::class.java,
            name = "habit-registry"
        ).build()
        val habitDAO = habitDatabase.HabitRegistryDAO()
        val logDAO = habitDatabase.HabitLogDAO()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiritfireTheme {
                /*MainUI(
                    singleHabitViewModel = singleHabitViewModel
                )*/
                MainUI(
                    habitDAO = habitDAO,
                    logDAO = logDAO,
                )
            }
        }
    }
}