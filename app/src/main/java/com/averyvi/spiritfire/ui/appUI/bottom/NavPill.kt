package com.averyvi.spiritfire.ui.appUI.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.ui.NavigationType
import com.averyvi.spiritfire.ui.RouteType
import com.averyvi.spiritfire.ui.Routes

val debug = true

@Composable
fun NavPill(
    currentRoute: Routes,
    navigateFunc: (NavigationType, Routes) -> Unit,
) { // general sites | menu button | habit sites
    /*
    general : tags, habits, log
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
                            navigateFunc = navigateFunc
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Routes.entries.filter { it.type == RouteType.GENERIC }
                        .forEachIndexed { index, route ->
                            NavPillButton(
                                route = route,
                                navigateFunc = navigateFunc
                            )
                        }
                }
            }
            Routes.entries.filter { it.type == RouteType.HOME }.forEachIndexed { index, route ->

                NavPillButton(
                    route = route,
                    navigateFunc = navigateFunc
                )
            }
            AnimatedVisibility(currentRoute.type == RouteType.HABIT || currentRoute.type == RouteType.HOME) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Routes.entries.filter { it.type == RouteType.HABIT }
                        .forEachIndexed { index, route ->
                            NavPillButton(
                                route = route,
                                navigateFunc = navigateFunc
                            )
                        }
                }
            }
        }
    }
}

@Composable
fun NavPillButton(
    route: Routes,
    navigateFunc: (NavigationType, Routes) -> Unit
) {
    Card(
        onClick = {
            navigateFunc(
                NavigationType.CUSTOM,
                route,
            )
        }
    ) {
        Text(stringResource(route.title))
    }
}