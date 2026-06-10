package com.averyvi.spiritfire.ui.screens

import androidx.compose.runtime.Composable
import com.averyvi.spiritfire.data.db.HabitLogUserDao
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.ui.testingdb

@Composable
fun HabitOverview(
    habitFilterViewModel: HabitFilterViewModel,
    habitDAO: HabitRegistryUserDao,
    logDAO: HabitLogUserDao
){
    //toto add log dao and table

    testingdb(
        habitDAO = habitDAO,
        logDAO = logDAO,
        habitFilterViewModel = habitFilterViewModel
    )
}