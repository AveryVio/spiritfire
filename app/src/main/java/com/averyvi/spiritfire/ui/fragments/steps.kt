package com.averyvi.spiritfire.ui.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.ui.components.ColumnSettingCard
import com.averyvi.spiritfire.ui.components.SettingCardName
import com.averyvi.spiritfire.ui.elements.FancyTextInput
import com.averyvi.spiritfire.ui.elements.SimpleTextInput

@Composable
fun StepsChange(){
    val habitStepsType = remember { mutableStateOf(false) }
    val habitStepsAmount = remember { mutableStateOf("") }
    val habitStepsComplete = remember { mutableStateOf("") }
    val habitStepsStrings = remember { mutableStateListOf<String>() }

    ColumnSettingCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingCardName(
                    text = stringResource(R.string.StepsCard),
                    textColor = MaterialTheme.colorScheme.tertiary,
                    fontSize = 25.sp
                )
                Switch(
                    checked = habitStepsType.value,
                    onCheckedChange = { habitStepsType.value = it },
                    thumbContent =
                        if(habitStepsType.value){ {Text("2+")}}
                        else{{Text("2")} },
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (habitStepsType.value){
                    FancyTextInput(
                        label = { Text(stringResource(R.string.StepsAmount)) },
                        placeholder = { Text(stringResource(R.string.placeholdertext)) },
                        value = habitStepsAmount.value,
                        onValueChange = {
                            if (it.length < 4) {
                                habitStepsAmount.value = it.filter{ numb -> numb.isDigit() } // todo add a signal to the user that 20 is the max

                                if (habitStepsAmount.value.isNotEmpty()) {
                                    if (habitStepsAmount.value.toInt() > 20) {
                                        habitStepsAmount.value = "20"
                                    }
                                    while (habitStepsStrings.size < habitStepsAmount.value.toInt() ) {
                                        habitStepsStrings.add("")
                                    }
                                    while (habitStepsStrings.size > habitStepsAmount.value.toInt() ) {
                                        habitStepsStrings.removeAt(habitStepsStrings.size - 1)
                                    }
                                } else {
                                    while (habitStepsStrings.isNotEmpty()) {
                                        habitStepsStrings.removeAt(habitStepsStrings.size - 1)
                                    }
                                }


                            }
                        },
                        brushColorList = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                        ),
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                    SimpleTextInput(
                        label = { Text(stringResource(R.string.StepsComplete)) },
                        placeholder = { Text(stringResource(R.string.placeholdertext)) },
                        value = habitStepsComplete.value,
                        onValueChange = {
                            if (it.length < 4) {
                                habitStepsComplete.value = it.filter{ numb -> numb.isDigit() }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (habitStepsType.value){
                    habitStepsStrings.forEachIndexed { index, string ->
                        SimpleTextInput(
                            label = { Text(stringResource(R.string.StepsComplete)) },
                            placeholder = { Text(stringResource(R.string.placeholdertext)) },
                            value = string,
                            onValueChange = {
                                if (it.length < 30) {
                                    habitStepsStrings[index] = it
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction =
                                    if(index == habitStepsAmount.value.toInt()) ImeAction.Done
                                    else ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                        )
                    }
                }
            }
        }
    }
}