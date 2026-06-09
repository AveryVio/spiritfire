package com.averyvi.spiritfire.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.ui.bottom.AppBottomSheet
import com.averyvi.spiritfire.ui.bottom.NavPill
import com.averyvi.spiritfire.ui.screens.HabitOverview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    habitDAO: HabitRegistryUserDao
){
    val filterVMfactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitFilterViewModel(habitDAO) as T
        }
    }
    val habitFilterViewModel: HabitFilterViewModel = viewModel(factory = filterVMfactory)


    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun NavControllerNavigate(routeNavType: RouteNavType){
        navController.navigate(
            route = DecideNextRoute(
                currentRoute,
                routeNavType
            ).name
        )
    }

    fun expandBottomSheet(
        scaffoldState: BottomSheetScaffoldState,
        scope: CoroutineScope
    ) {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    Box() {
        val scaffoldState = rememberBottomSheetScaffoldState()
        val scope = rememberCoroutineScope()

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
            state = rememberTopAppBarState(),
            canScroll = { true }
        )

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            //modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            topBar = {
            },
            sheetPeekHeight = 64.dp + 16.dp,
            sheetContent = @Composable {
                AppBottomSheet(
                    habitFilterViewModel = habitFilterViewModel
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {


                NavHost(
                    navController = navController,
                    startDestination = Routes.HabitOverview.name,
                ) {

                    val onRouteButtonClicked = { route: Routes ->
                        navController.navigate(route = route.name)
                    }

                    composable(route = Routes.NewHabit.name) {
                        Column() {
                            Text("fjsklfdj")
                            Text("fjsklfdj")
                            Button(onClick = {
                                expandBottomSheet(scaffoldState, scope)
                            }) { Text("exp load") }
                            Button(onClick = {
                                thread {
                                    habitDAO.insert(
                                        generateRandomHabitEntity()
                                    )
                                }.join()
                            }) { Text("add") }
                            Button(onClick = {
                                thread {
                                    habitDAO.getAll().forEach { habitDAO.delete(it) }
                                }.join()
                            }) { Text("remove") }
                            testingdb(
                                habitDAO = habitDAO,
                                habitFilterViewModel = habitFilterViewModel
                            )
                        }
                    }

                    composable(route = Routes.HabitOverview.name) {
                        HabitOverview(
                            habitFilterViewModel = habitFilterViewModel,
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    val sheetOffsetY = try {
                        scaffoldState.bottomSheetState.requireOffset().toInt()
                    } catch (e: IllegalStateException) {
                        Int.MAX_VALUE
                    }

                    val yOffsetAdjustment = 80.dp.roundToPx()

                    IntOffset(
                        x = 0,
                        y = if (sheetOffsetY == Int.MAX_VALUE) 0 else sheetOffsetY - yOffsetAdjustment
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            NavPill(navigateFunc = { NavControllerNavigate(it) })
        }
    }
}