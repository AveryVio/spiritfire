package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        modifier = Modifier.size(size),
        enabled = complete,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(icon),
            modifier = Modifier.size(Dp(size.value * 0.9f)).padding(start = Dp(size.value * 0.1f), top = Dp(size.value * 0.1f), ),
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
            modifier = Modifier.size((size.value * 1.55).dp)
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

@Composable
fun SmallPill(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(256.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            contentColor = color,
            containerColor = color.copy(
                alpha = color.alpha * 0.5f ,
                red = color.red * 0.8f,
                blue = color.blue * 0.8f,
                green = color.green * 0.8f,
            ),
            disabledContentColor = color.copy(
                red = color.red * 0.7f,
                blue = color.blue * 0.7f,
                green = color.green * 0.7f,
            ),
            disabledContainerColor = color.copy(
                alpha = color.alpha * 0.5f,
                red = color.red * 0.7f,
                blue = color.blue * 0.7f,
                green = color.green * 0.7f,
            )
        )
    ) {
        Box(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            content()
        }
    }
}

@Composable
fun IconPillWithValue(
    icon: Int,
    value: Int,
    colour: Color,
) {
    SmallPill(
        color = colour
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(icon),
                contentDescription = null,
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}