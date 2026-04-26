package com.averyvi.spiritfire.ui.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.viewmodels.SingleHabitViewModel
import com.averyvi.spiritfire.ui.components.ColorDropdownPicker
import com.averyvi.spiritfire.ui.components.ColumnSettingCard
import com.averyvi.spiritfire.ui.components.IconDropdownPicker
import com.averyvi.spiritfire.ui.components.SettingCardName
import com.averyvi.spiritfire.ui.elements.FancyTextInput
import com.averyvi.spiritfire.ui.elements.SimpleTextInput

@Composable
fun BasicChange(
    singleHabitViewModel: SingleHabitViewModel
){
    val habitname = singleHabitViewModel.habit.collectAsState().value.name
    val habitdesc = singleHabitViewModel.habit.collectAsState().value.description

    ColumnSettingCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SettingCardName(
                text = stringResource(R.string.BasicHabitInfoChangeCard),
                textColor = MaterialTheme.colorScheme.primary,
                fontSize = 35.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(56.dp)
            ) {
                IconDropdownPicker( singleHabitViewModel = singleHabitViewModel )
                ColorDropdownPicker( singleHabitViewModel = singleHabitViewModel )
                FancyTextInput(
                    label = { Text(stringResource(R.string.HabitName)) },
                    placeholder = { Text(stringResource(R.string.placeholdertext)) },
                    value = habitname,
                    onValueChange = { singleHabitViewModel.changeHabitValue(newName = it) },
                    modifier = Modifier.fillMaxWidth(),
                    brushColorList = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary
                    ),
                    gradientTextStyle = TextStyle(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }
            SimpleTextInput(
                label = { Text(stringResource(R.string.HabitDesc)) },
                placeholder = { Text(stringResource(R.string.placeholdertext)) },
                value = habitdesc,
                onValueChange = {
                    if (it.lines().size < 4) {
                        singleHabitViewModel.changeHabitValue(newDescription = it)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = if (habitdesc.lines().size < 3) ImeAction.None else ImeAction.Done),
                maxLines = 3
            )
        }
    }
}