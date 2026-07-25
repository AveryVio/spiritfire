package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.habits.SColour

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

@Composable
fun CompletionChip(
    color: Color = SColour.Grey.color,
    size: Dp = 32.dp
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.size(size)
    ) { }
}