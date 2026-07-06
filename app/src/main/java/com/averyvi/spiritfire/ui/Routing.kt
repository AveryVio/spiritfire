package com.averyvi.spiritfire.ui

import androidx.annotation.StringRes
import com.averyvi.spiritfire.R

enum class Routes(
    @field:StringRes val title: Int,
){
    TestingScreen(
        title = R.string.TestingScreen
    ),

    HabitOverview(
        title = R.string.HabitOverviewScreen
    ),

    AllHabits(
        title = R.string.AllHabitsScreen
    ),

    NewHabit(
        title = R.string.NewHabitScreen
    ),
}

enum class RouteNavType() {
    PRIMARY,
    SECONDARY,
    HOME,
    CUSTOM,
}

fun DecideNextRoute(
    currentRoute: String?,
    routeNavType: RouteNavType,
    intendedDestination: Routes = Routes.HabitOverview
): Routes {
    when(routeNavType){
        RouteNavType.PRIMARY -> {
            return when(currentRoute){
                Routes.HabitOverview.name -> { Routes.NewHabit }
                Routes.NewHabit.name -> { Routes.HabitOverview }
                else -> { Routes.HabitOverview }
            }
        }
        RouteNavType.SECONDARY -> {

        }
        RouteNavType.HOME -> {

        }
        RouteNavType.CUSTOM -> {
            return when(intendedDestination) {
                Routes.TestingScreen -> {
                    Routes.TestingScreen
                }
                Routes.AllHabits -> {
                    Routes.AllHabits
                }
                Routes.HabitOverview -> {
                    Routes.HabitOverview
                }
                Routes.NewHabit -> {
                    Routes.NewHabit
                }
            }
        }
        else -> {

        }
    }
    return Routes.HabitOverview
}