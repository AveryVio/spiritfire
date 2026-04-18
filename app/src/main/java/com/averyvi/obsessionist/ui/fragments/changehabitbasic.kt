package com.averyvi.spiritfire.ui.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.ResetDaysInterval
import com.averyvi.spiritfire.data.definitions.ResetDaysIntervalUnit
import com.averyvi.spiritfire.data.definitions.ResetTime
import com.averyvi.spiritfire.ui.components.ColorDropdownPicker
import com.averyvi.spiritfire.ui.components.ColumnSettingCard
import com.averyvi.spiritfire.ui.components.IconDropdownPicker
import com.averyvi.spiritfire.ui.components.ResetUnitDropdownPicker
import com.averyvi.spiritfire.ui.components.SettingCardName
import com.averyvi.spiritfire.ui.components.timePicker
import com.averyvi.spiritfire.ui.components.timePickerOverlay
import com.averyvi.spiritfire.ui.elements.SimpleTextInput

@Composable
fun BasicChange(){
    val habitname = remember { mutableStateOf("") }
    val habitdesc = remember { mutableStateOf("") }

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
                IconDropdownPicker()
                ColorDropdownPicker()
                SimpleTextInput(
                    label = { Text(stringResource(R.string.HabitName)) },
                    placeholder = { Text(stringResource(R.string.placeholdertext)) },
                    value = habitname.value,
                    onValueChange = { habitname.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    brushColorList = listOf(
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
                value = habitdesc.value,
                onValueChange = {
                    if(it.lines().size < 4) {
                        habitdesc.value = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                brushColorList = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary
                ),
                gradientTextStyle = TextStyle(),
                keyboardOptions = KeyboardOptions( imeAction = if(habitdesc.value.lines().size < 3) ImeAction.None else ImeAction.Done ),
                maxLines = 3
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeChange(){

    var daysResetInterval: ResetDaysInterval
    val selectedUnit = remember { mutableStateOf(ResetDaysIntervalUnit.DAILY) }
    val ResetDaysValue = remember { mutableStateOf("") }
    val resetTime = remember { mutableStateOf(ResetTime(hour = 0, minute = 0)) }

    val isTimePickerVisible = remember { mutableStateOf(false) }

    ColumnSettingCard {
        Column() {
            SettingCardName(
                text = stringResource(R.string.HabitTimeChangeCard),
                textColor = MaterialTheme.colorScheme.secondary,
                fontSize = 27.sp
            )
            //interval day
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(56.dp)
            ) {
                ResetUnitDropdownPicker(
                    selectedUnit = selectedUnit.value,
                    onUnitChange = { selectedUnit.value = it },
                )
                if(selectedUnit.value == ResetDaysIntervalUnit.CUSTOM_DAYS) {
                    SimpleTextInput(
                        label = { Text(stringResource(R.string.IntervalValueSetName)) },
                        placeholder = { Text(stringResource(R.string.placeholdertext)) },
                        value = ResetDaysValue.value,
                        onValueChange = {
                            if (it.length < 30) {
                                ResetDaysValue.value = it.filter { numb -> numb.isDigit() }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        brushColorList = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.tertiary
                        ),
                        gradientTextStyle = TextStyle(),
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                }
                Button(onClick = { isTimePickerVisible.value = true }) {
                    Text("Show Overlay")
                }
                timePicker()
                timePickerOverlay(
                    isPickerVisible = isTimePickerVisible.value,
                    hidePicker = { isTimePickerVisible.value = false },
                    onConfirm = {
                        resetTime.value = it
                    }
                )
            }
        }
    }

}

@Composable
fun PrioritySortingChange(){
    //priority
}