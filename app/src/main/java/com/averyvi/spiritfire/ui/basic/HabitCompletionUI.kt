package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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

@Composable
fun CompletionStreakUI(
    completion: List<Boolean>,
    grace: List<Boolean>,
    maxShownInputLength: Int,
    chipSize: Dp,
    spacesBetween: Dp,
    rowModifier: Modifier = Modifier
) {
    val completion = if(completion.size > maxShownInputLength) completion.subList(0,maxShownInputLength) else completion
    val grace = if(grace.size > maxShownInputLength) grace.subList(0,maxShownInputLength) else grace

    Row(
        horizontalArrangement = Arrangement.spacedBy(spacesBetween),
        modifier = rowModifier
            .fakeFadingEdge(
                Brush.horizontalGradient(
                    0.7f to Color.Transparent,
                    0.95f to MaterialTheme.colorScheme.surfaceContainer,
                )
            )
    ) {
        completion.forEachIndexed { index, bool ->
            Card(
                modifier = Modifier
                    .size(chipSize),
                colors = CardDefaults.cardColors().copy(
                    containerColor = if(completion[index]) {
                        MaterialTheme.colorScheme.primary
                    } else if(grace[index]) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            ) {}
        }
    }
}