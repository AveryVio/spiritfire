package com.averyvi.obsessionist.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumnSettingCard(
    content: @Composable () -> Unit
){
    Card(
        modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    ) {
        Box(
            Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
        ) {
            content()
        }
    }
}

@Composable
fun SettingCardName(
    text:String,
    textColor: Color,
    fontSize: TextUnit,
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Black,
            color = textColor,
            fontSize = fontSize
        )
    )
}