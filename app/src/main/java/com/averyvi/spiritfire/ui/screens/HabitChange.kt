package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.ui.appUI.top.EditingAppBar
import com.averyvi.spiritfire.ui.appUI.top.GeneralAppBar
import com.averyvi.spiritfire.ui.basic.FancyTextInput

@Composable
fun HabitChange(
    outerPadding: PaddingValues
) {


    val variousContentScroll = rememberScrollState()

    var texttest = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.padding(outerPadding),
        topBar = {
            EditingAppBar()
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(8.dp).padding(innerPadding)
                .verticalScroll(variousContentScroll),
            verticalArrangement = Arrangement.spacedBy(4.dp),

        ) {
            // name
            FancyTextInput(
                label = @Composable { Text("jflkdsjf") },
                placeholder = @Composable { Text("jflkdsjf") },
                value = texttest.value,
                onValueChange = {texttest.value = it},
                brushColorList = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            // desc
            // colour
            // icon
            // checks
            // reset
            // grace
            // cooldown
            // priority, difficulty
            // tags
        }
    }
}