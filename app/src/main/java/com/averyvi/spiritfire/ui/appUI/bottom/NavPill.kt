package com.averyvi.spiritfire.ui.appUI.bottom

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import com.averyvi.spiritfire.data.sources.HabitRepository
import com.averyvi.spiritfire.ui.NavigationType
import com.averyvi.spiritfire.ui.RouteType
import com.averyvi.spiritfire.ui.Routes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.averyvi.spiritfire.data.definitions.habits.SColour
import kotlinx.coroutines.CoroutineScope

val debug = true

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun NavPill(
    habitFilterViewModel: HabitFilterViewModel,
    habitRepository: HabitRepository,
    currentRoute: Routes,
    navigateFunc: (NavigationType, Routes) -> Unit,
) { // general sites | menu button | habit sites
    val viewModelScope = null
    val currentHabitRow: HabitRow = if(habitFilterViewModel.shownDetail.collectAsState().value != 0) {
        habitFilterViewModel.shownDetail
            .flatMapLatest {
                habitRepository.getSelectHabits(listOf(it)).map { list ->
                    list.first()
                }
            }
    } else {
        object : Flow<HabitRow> {
            override suspend fun collect(collector: FlowCollector<HabitRow>) {
                HabitRow.NO_HABIT
            }
        }
    }.collectAsState(HabitRow.NO_HABIT).value

    /*
    general : tags, habits
    menu
    habit: overview, analytics
    */
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if(debug) {
            Row() {
                Routes.entries.filter { it.type == RouteType.EDITOR }
                    .forEachIndexed { index, route ->
                        NavPillButton(
                            route = route,
                            navigateFunc = navigateFunc,
                        )
                    }
                Routes.entries.filter { it.type == RouteType.SYSTEM }
                    .forEachIndexed { index, route ->
                        NavPillButton(
                            route = route,
                            navigateFunc = navigateFunc
                        )
                    }
            }
        }
        val scrollState = rememberScrollState()
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            AnimatedVisibility(currentRoute.type == RouteType.GENERIC || currentRoute.type == RouteType.HOME) {
                Card(
                    colors = CardDefaults.cardColors().copy(
                        containerColor = SColour.JaOrange.color.copy(
                            red = SColour.JaOrange.color.red * 0.5f,
                            green = SColour.JaOrange.color.green * 0.5f,
                            blue = SColour.JaOrange.color.blue * 0.5f,
                        )
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Routes.entries.filter { it.type == RouteType.GENERIC }
                            .forEachIndexed { index, route ->
                                NavPillButton(
                                    route = route,
                                    navigateFunc = navigateFunc,
                                    color = SColour.JaOrange.color
                                )
                            }
                    }
                }
            }
            Routes.entries.filter { it.type == RouteType.HOME }.forEachIndexed { index, route ->
                Card(
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.tertiary.copy(
                            red = MaterialTheme.colorScheme.tertiary.red * 0.5f,
                            green = MaterialTheme.colorScheme.tertiary.green * 0.5f,
                            blue = MaterialTheme.colorScheme.tertiary.blue * 0.5f,
                        )
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        NavPillButton(
                            route = route,
                            navigateFunc = navigateFunc,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
            AnimatedVisibility(currentRoute.type == RouteType.HABIT || currentRoute.type == RouteType.HOME) {
                Card(
                    colors = CardDefaults.cardColors().copy(
                        containerColor = currentHabitRow.colour.copy(
                            red = currentHabitRow.colour.red * 0.5f,
                            green = currentHabitRow.colour.green * 0.5f,
                            blue = currentHabitRow.colour.blue * 0.5f,
                        )
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Routes.entries.filter { it.type == RouteType.HABIT }
                            .forEachIndexed { index, route ->
                                NavPillButton(
                                    route = route,
                                    navigateFunc = navigateFunc,
                                    color = currentHabitRow.colour
                                )
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun NavPillButton(
    route: Routes,
    navigateFunc: (NavigationType, Routes) -> Unit,
    color: Color = SColour.Grey.color,
) {
    Card(
        onClick = {
            navigateFunc(
                NavigationType.CUSTOM,
                route,
            )
        },
        colors = CardDefaults.cardColors().copy(
            containerColor = color.copy(
                red = color.red * 0.8f,
                green = color.green * 0.8f,
                blue = color.blue * 0.8f,
            )
        )
    ) {
        Text(stringResource(route.title))
    }
}