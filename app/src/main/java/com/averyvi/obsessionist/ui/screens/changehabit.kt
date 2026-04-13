package com.averyvi.obsessionist.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.averyvi.obsessionist.ui.elements.ObsTextInput
import com.averyvi.obsessionist.ui.fragments.BasicChange
import com.averyvi.obsessionist.ui.fragments.TimeChange

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