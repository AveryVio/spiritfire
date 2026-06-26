package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HabitCheckIcon(
    icon: Int,
    colour: Color,
    complete: Boolean = false,
    onClick: () -> Unit = {},
    size: Dp,
) {
    Card(
        shape = CircleShape,
        colors = CardColors(
            contentColor = MaterialTheme.colorScheme.surface,
            containerColor = colour,
            disabledContentColor = colour,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        enabled = complete,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(icon),
            modifier = Modifier.padding((size.value * 0.1).dp).size(size),
            contentDescription = null
        )
    }
}

@Composable
fun CircularHabitProgress(
    icon: Int,
    colour: Color,
    complete: Boolean = false,
    onClick: () -> Unit = {},
    progress: Float,
    size: Dp,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        HabitCheckIcon(
            icon = icon,
            colour = colour,
            complete = complete,
            onClick = onClick,
            size = size
        )
        CircularProgressIndicator(
            progress = { progress },
            strokeWidth = (size.value * 0.1).dp,
            modifier = Modifier.size((size.value * 1.3).dp)
        )
    }
}

@Composable
fun LinearIconifiedProgress(
    iconCount: Int,
    icon: Int,
    value: Int,
    colour: Color,
    size: Dp
) {
    Row() {
        for (currentIcon in 1..iconCount){
            Icon(
                painter = painterResource(icon),
                modifier = Modifier.padding((size.value * 0.1).dp).size(size),
                tint = if(value < currentIcon) MaterialTheme.colorScheme.onSurfaceVariant else colour,
                contentDescription = null
            )
        }
    }
}