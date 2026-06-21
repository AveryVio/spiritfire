package com.averyvi.spiritfire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.data.sources.OfflineFirstHabitRepository
import com.averyvi.spiritfire.old.ui.theme.SpiritfireTheme
import com.averyvi.spiritfire.ui.MainUI
import com.averyvi.spiritfire.data.sources.db.HabitRegistry

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val habitDatabase = Room.databaseBuilder(
            context = applicationContext,
            klass = HabitRegistry::class.java,
            name = "habit-db"
        ).build()
        val habitDAO = habitDatabase.HabitRegistryDAO()
        val logDAO = habitDatabase.HabitLogDAO()
        val tagDAO = habitDatabase.TagDAO()
        val habitCrossRefDAO = habitDatabase.HabitTagCrossRefDAO()
        val habitRepository: HabitRepository = OfflineFirstHabitRepository(habitDAO, logDAO, tagDAO, habitCrossRefDAO)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiritfireTheme {
                /*MainUI(
                    singleHabitViewModel = singleHabitViewModel
                )*/
                MainUI(
                    habitRepository = habitRepository
                )
            }
        }
    }
}