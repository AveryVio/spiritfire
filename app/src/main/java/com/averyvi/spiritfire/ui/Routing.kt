package com.averyvi.spiritfire.ui

import androidx.annotation.StringRes
import com.averyvi.spiritfire.R

enum class Routes(
    @field:StringRes val title: Int,
    val type: RouteType,
){
    TestingScreen(
        title = R.string.TestingScreen,
        type = RouteType.SYSTEM
    ),

    HabitOverview(
        title = R.string.HabitOverviewScreen,
        type = RouteType.HOME
    ),

    DetailedHabitAnalytics(
        title = R.string.DetailedHabitAnalyticsScreen,
        type = RouteType.HABIT
    ),

    AllHabits(
        title = R.string.AllHabitsScreen,
        type = RouteType.GENERIC
    ),

    NewHabit(
        title = R.string.NewHabitScreen,
        type = RouteType.EDITOR
    ),
}

enum class RouteType {
    HOME,
    SYSTEM,
    GENERIC,
    HABIT,
    EDITOR,
}

enum class NavigationType() {
    PRIMARY,
    SECONDARY,
    HOME,
    CUSTOM,
}

fun DecideNextRoute(
    currentRoute: Routes,
    navigationType: NavigationType,
    intendedDestination: Routes = Routes.HabitOverview
): Routes {
    when(navigationType){
        NavigationType.PRIMARY -> {
            return when(currentRoute){
                Routes.HabitOverview -> { Routes.NewHabit }
                Routes.NewHabit -> { Routes.HabitOverview }
                Routes.AllHabits -> { Routes.DetailedHabitAnalytics }
                Routes.DetailedHabitAnalytics -> { Routes.AllHabits }
                else -> { Routes.HabitOverview }
            }
        }
        NavigationType.SECONDARY -> {

        }
        NavigationType.HOME -> {

        }
        NavigationType.CUSTOM -> {
            return intendedDestination
        }
        else -> {

        }
    }
    return Routes.HabitOverview
}