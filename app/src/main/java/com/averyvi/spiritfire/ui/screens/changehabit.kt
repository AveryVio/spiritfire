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
import com.averyvi.spiritfire.ui.fragments.BasicChange
import com.averyvi.spiritfire.ui.fragments.DateChange
import com.averyvi.spiritfire.ui.fragments.StepsChange

@Composable
fun ChangeHabitScreen(
    modifier: Modifier = Modifier
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
            text = "jgfkldsjgfl",
            textColor = MaterialTheme.colorScheme.onSurface,
            fontSize = 50.sp
        )
        BasicChange()
        DateChange()
        StepsChange()
    }
}