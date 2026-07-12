package com.averyvi.spiritfire.ui.appUI.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
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
        Routes.entries.forEachIndexed { index, routes ->
            Card(
                onClick = {
                    navigateFunc(
                        RouteNavType.CUSTOM,
                        routes,
                    )
                }
            ) {
                Text(stringResource(routes.title))
            }
        }
    }
}