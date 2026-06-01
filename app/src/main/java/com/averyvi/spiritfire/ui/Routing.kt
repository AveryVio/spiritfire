package com.averyvi.spiritfire.ui

import androidx.annotation.StringRes
import com.averyvi.spiritfire.R

enum class Routes(
    @field:StringRes val title: Int,
){
    HabitOverview(
        title = R.string.HabitOverviewScreen
    ),
}

enum class NavType() {
    PRIMARY,
    SECONDARY,
    HOME,
    CUSTOM,
}

fun DecideNextRoute(
    currentRoute: Routes,
    navType: NavType,
    intendedDestination: Routes = Routes.HabitOverview
): Routes {
    when(navType){
        NavType.PRIMARY -> {

        }
        NavType.SECONDARY -> {

        }
        NavType.HOME -> {
            return Routes.HabitOverview
        }
        NavType.CUSTOM -> {

        }
        else -> {

        }
    }
    return Routes.HabitOverview
}