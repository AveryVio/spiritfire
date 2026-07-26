package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ShowRowsOfItems(
    itemsCount: Int,
    itemsInRow: Int,
    item: @Composable (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        var itemsRemaining = 0
        while ( itemsRemaining < itemsCount ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                for (row in (0..<itemsInRow)) {
                    if (itemsRemaining < itemsCount) {
                        item(itemsRemaining)
                        itemsRemaining++
                    } else { break }
                }
            }
        }
    }
}

