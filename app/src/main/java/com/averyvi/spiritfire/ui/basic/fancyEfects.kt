package com.averyvi.spiritfire.ui.basic

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.fadingEdge(
    brush: Brush
) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush = brush, blendMode = BlendMode.DstIn)
        }
    }

fun Modifier.fakeFadingEdge(
    brush: Brush
) = this.drawWithContent {
    drawContent()
    drawRect(brush = brush)
}