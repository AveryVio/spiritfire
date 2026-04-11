package com.averyvi.obsessionist.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.averyvi.obsessionist.R
import com.averyvi.obsessionist.ui.screens.RenameTileScreen

@Composable
fun MainUI(){

    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.RenameTile.name
            ) {
                composable(route = Routes.Intro.name) {

                }
                composable(route = Routes.RenameTile.name) {
                    RenameTileScreen()
                }
            }
        }
    }
}

enum class Routes(title: Int){
    Intro(
        title = R.string.IntroScreen
    ),
    RenameTile(
        title = R.string.RenameTileScreen
    ),
}