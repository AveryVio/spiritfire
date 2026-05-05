package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.ui.components.SettingCardName

@Composable
fun MultipleHabitScreen(
){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize().padding(
            top = 8.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 4.dp)
    ) {
        SettingCardName( // will make a custom title element
            text = "multihabitscreen",
            textColor = MaterialTheme.colorScheme.onSurface,
            fontSize = 50.sp
        )
    }
}