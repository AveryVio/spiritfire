package com.averyvi.spiritfire.ui.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun NavPill(
    changeSheetVisibility: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card() {
            Text("left")
        }
        Card() {
            Text("jfsdk")
        }
        Card(
            onClick = { changeSheetVisibility(true) }
        ) {
            Text("show")
        }
    }
}