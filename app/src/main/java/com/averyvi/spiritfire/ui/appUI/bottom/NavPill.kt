package com.averyvi.spiritfire.ui.appUI.bottom

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.ui.RouteNavType
import com.averyvi.spiritfire.ui.Routes

@Composable
fun NavPill(
    navigateFunc: (RouteNavType, Routes) -> Unit,
) {
    val scrollState = rememberScrollState()
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.horizontalScroll(scrollState)
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