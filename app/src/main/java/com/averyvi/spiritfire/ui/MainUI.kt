package com.averyvi.spiritfire.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.viewmodels.SingleHabitViewModel
import com.averyvi.spiritfire.ui.screens.ChangeHabitScreen
import com.averyvi.spiritfire.ui.screens.MultipleHabitScreen

@Composable
fun MainUI(
    singleHabitViewModel: SingleHabitViewModel
){

    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.ChangeHabit.name
            ) {
                composable(route = Routes.Intro.name) {

                }
                composable(route = Routes.ChangeHabit.name) {
                    ChangeHabitScreen(
                        singleHabitViewModel = singleHabitViewModel
                    )
                }
                composable(route = Routes.MultipleHabits.name) {
                    MultipleHabitScreen()
                }
            }
        }
    }
}

enum class Routes(title: Int){
    Intro(
        title = R.string.IntroScreen
    ),
    ChangeHabit(
        title = R.string.ChangeHabitScreen
    ),
    MultipleHabits(
        title = R.string.MultipleHabitsView
    )
}