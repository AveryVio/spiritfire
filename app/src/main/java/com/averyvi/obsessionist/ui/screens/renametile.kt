package com.averyvi.obsessionist.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.averyvi.obsessionist.ui.elements.ObsTextInput

@Composable
fun RenameTileScreen(
    modifier: Modifier = Modifier
){
    val tilename = remember { mutableStateOf("") }
    ObsTextInput(
        label = {Text("name")},
        placeholder = {Text("placeholder")},
        value = tilename.value,
        onValueChange = { tilename.value = it },
        modifier = Modifier,
        brushColorList = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary),
        gradientTextStyle = TextStyle(),
    )
}