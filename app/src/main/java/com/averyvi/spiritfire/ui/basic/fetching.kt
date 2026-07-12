package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FlowPillButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable () -> Unit = {},
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