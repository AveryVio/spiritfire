package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.averyvi.spiritfire.data.db.HabitLogUserDao
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.ui.components.UICard
import com.averyvi.spiritfire.ui.testingdb

@Composable
fun HabitOverview(
    habitFilterViewModel: HabitFilterViewModel,
    habitDAO: HabitRegistryUserDao,
    logDAO: HabitLogUserDao
){
    //toto add log dao and table
    Column() {
    }

    testingdb(
        habitDAO = habitDAO,
        logDAO = logDAO,
        habitFilterViewModel = habitFilterViewModel
    )
}