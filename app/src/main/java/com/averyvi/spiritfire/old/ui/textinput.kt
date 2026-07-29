package com.averyvi.spiritfire.old.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
/*
@Composable
fun FancyTextInput(
    label: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.Companion,
    brushColorList: List<Color> = listOf( MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary ),
    gradientTextStyle: TextStyle = TextStyle(fontSize = TextUnit.Unspecified),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
){
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = value,
        style = gradientTextStyle
    )
    val gradientEndX = if (textLayoutResult.size.width > 0) {
        textLayoutResult.size.width.toFloat()
    } else {
        1f
    }

    TextField(
        label = label,
        placeholder = placeholder,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(24.dp),
        textStyle = TextStyle().copy(
            fontSize = 20.sp,
            brush = Brush.linearGradient(
                colors = brushColorList,
                end = Offset(gradientEndX, 0f)
            )
        ),
        keyboardOptions = keyboardOptions,
        singleLine = maxLines == 1,
        maxLines = maxLines
    )
}

@Composable
fun SimpleTextInput(
    label: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.Companion,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
){

    TextField(
        label = label,
        placeholder = placeholder,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        textStyle = TextStyle().copy(
            fontSize = 18.sp,
        ),
        keyboardOptions = keyboardOptions,
        singleLine = maxLines == 1,
        maxLines = maxLines
    )
}*/