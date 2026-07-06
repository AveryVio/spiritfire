package com.averyvi.spiritfire.ui.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.ui.RouteNavType
import com.averyvi.spiritfire.ui.Routes

@Composable
fun NavPill(
    navigateFunc: (RouteNavType, Routes) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            onClick = {
                navigateFunc(
                    RouteNavType.CUSTOM,
                    Routes.HabitOverview
                )
            }
        ) {
            Text("Overview")
        }
        Card(
            onClick = {
                navigateFunc(
                    RouteNavType.CUSTOM,
                    Routes.AllHabits
                )
            }
        ) {
            Text("Home")
        }
        Card(
            onClick = {
                navigateFunc(
                    RouteNavType.CUSTOM,
                    Routes.TestingScreen
                )
            }
        ) {
            Text("Testing")
        }
    }
}