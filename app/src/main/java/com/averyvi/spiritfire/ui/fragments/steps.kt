package com.averyvi.spiritfire.ui.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.averyvi.spiritfire.data.definitions.habitConsts
import com.averyvi.spiritfire.data.viewmodels.SingleHabitViewModel
import com.averyvi.spiritfire.ui.components.ColumnSettingCard
import com.averyvi.spiritfire.ui.components.SettingCardName
import com.averyvi.spiritfire.ui.elements.FancyTextInput
import com.averyvi.spiritfire.ui.elements.SimpleTextInput

@Composable
fun StepsChange(
    singleHabitViewModel: SingleHabitViewModel,
){
    val habitStepsType = remember { mutableStateOf(false) }
    val habitStepsAmount = singleHabitViewModel.habit.collectAsState().value.stepsAmount
    val habitStepsComplete = singleHabitViewModel.habit.collectAsState().value.stepsComplete
    val habitStepsStrings = singleHabitViewModel.habit.collectAsState().value.stepsStrings

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
                        if(habitStepsType.value){ {Text("1+")}}
                        else{{Text("1")} },
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
                        value = habitStepsAmount,
                        onValueChange = {
                            if ((it.toIntOrNull() ?: 0) > habitConsts.maxSteps) {
                                singleHabitViewModel.changeHabitValue(newStepsAmount = habitConsts.maxSteps.toString())
                            } else {
                                singleHabitViewModel.changeHabitValue(newStepsAmount = it.filter{ numb -> numb.isDigit() } ) // todo add a signal to the user that 20 is the max
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
                        value = habitStepsComplete,
                        onValueChange = {
                            if ((it.toIntOrNull() ?: 0) > habitConsts.maxSteps) {
                                singleHabitViewModel.changeHabitValue(newStepsComplete =  habitConsts.maxSteps.toString())
                            } else {
                                singleHabitViewModel.changeHabitValue(newStepsComplete = it.filter{ numb -> numb.isDigit() }) // todo add a signal to the user that 20 is the max
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
                    if(habitStepsAmount.isNotEmpty()) {
                        habitStepsStrings.take(habitStepsAmount.toIntOrNull() ?: 0).forEachIndexed { index, string ->
                            SimpleTextInput(
                                label = { Text(stringResource(R.string.StepsComplete)) },
                                placeholder = { Text(stringResource(R.string.placeholdertext)) },
                                value = string,
                                onValueChange = {
                                    if (it.length < 30) {
                                        singleHabitViewModel.changeHabitString(index, it)
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction =
                                        if(habitStepsAmount.isNotEmpty()) {
                                            if (index >= (habitStepsAmount.toInt() - 1)) ImeAction.Done
                                            else ImeAction.Next
                                        } else {
                                            ImeAction.Search
                                        }
                                ),
                                modifier = Modifier.fillMaxWidth().height(56.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}