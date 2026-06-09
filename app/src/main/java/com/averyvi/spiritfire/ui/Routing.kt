package com.averyvi.spiritfire.ui

import androidx.annotation.StringRes
import com.averyvi.spiritfire.R

enum class Routes(
    @field:StringRes val title: Int,
){
    HabitOverview(
        title = R.string.HabitOverviewScreen
    ),

    NewHabit(
        title = R.string.NewHabitScreen
    )
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

        }
        else -> {

        }
    }
    return Routes.HabitOverview
}