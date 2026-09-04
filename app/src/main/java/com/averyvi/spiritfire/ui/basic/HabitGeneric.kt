package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.data.definitions.habits.SColour

@Composable
fun HabitCheckIcon(
    icon: Int,
    colour: Color,
    complete: Boolean = false,
    filled: Boolean = true,
    onClick: () -> Unit = {},
    size: Dp,
) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors().copy(
            contentColor = if (filled) { if (complete) MaterialTheme.colorScheme.surface else colour }
            else { if (complete) colour else colour.copy(alpha = colour.alpha * 0.3f ,).compositeOver(MaterialTheme.colorScheme.onSurface) },
            containerColor = if (filled) if (complete) colour else  MaterialTheme.colorScheme.surfaceContainer
            else Color.Transparent,
        ),
        modifier = Modifier.size(size),
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
            modifier = Modifier.size((size.value * 1.1).dp)
        )
    }
}

@Composable
fun LinearIconifiedProgress(
    iconCount: Int,
    icon: Int,
    value: Int,
    colour: Color,
    size: Dp,
    onClickItem: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (currentIcon in 1..iconCount){
            val currentColor = if(value < currentIcon) MaterialTheme.colorScheme.onSurfaceVariant else colour

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors().copy(
                    containerColor = currentColor.copy(
                        red = currentColor.red * 0.5f,
                        green = currentColor.green * 0.5f,
                        blue = currentColor.blue * 0.5f,
                    ),
                    contentColor = currentColor
                ),
                onClick = onClickItem
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        modifier = Modifier.padding((size.value * 0.1).dp).size(size),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun SmallPill(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    var modifier = modifier
        .clip(RoundedCornerShape(256.dp))
        .background(
            color.copy(
                alpha = color.alpha * 0.5f ,
                red = color.red * 0.8f,
                blue = color.blue * 0.8f,
                green = color.green * 0.8f,
            )
        )

    if (onClick != null) {
        modifier = modifier.clickable(onClick = onClick)
    }

    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
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

@Composable
fun WidthFlexibleChip(
    color: Color = SColour.Grey.color,
    height: Dp = 32.dp,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.height(height)
    ) { }
}