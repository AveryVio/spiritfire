package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.ui.fragments.BasicChange
import com.averyvi.spiritfire.ui.fragments.TimeChange

@Composable
fun ChangeHabitScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        BasicChange()
        TimeChange()
    }
}