package com.averyvi.spiritfire.old.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.old.data.definitions.ResetDaysIntervalUnit
import com.averyvi.spiritfire.old.data.definitions.ResetTime
import java.util.Calendar

@Composable
fun timePicker(
    resetTime: ResetTime,
    onResetTime: (ResetTime) -> Unit,
    modifier: Modifier,
) {
    val isTimePickerVisible = remember { mutableStateOf(false) }

    Card(
        onClick = { isTimePickerVisible.value = true },
        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                )
            ) { Text(
                text = resetTime.hour.toString(),
                fontSize = 45.sp,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),

            ) }
            Text(
                text = ":",
                fontSize = 25.sp,
                modifier = Modifier.weight(1f),
            )
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                )
            ) { Text(
                text = resetTime.minute.toString(),
                fontSize = 45.sp,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            ) }
        }

        timePickerOverlay(
            isPickerVisible = isTimePickerVisible.value,
            hidePicker = { isTimePickerVisible.value = false },
            onConfirm = onResetTime
        )
    }
}

@Composable
fun datePicker(
    ResetDaysUnit: ResetDaysIntervalUnit,
    onUnitChange: (ResetDaysIntervalUnit) -> Unit,
    ResetDaysValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        ResetUnitDropdownPicker(
            selectedUnit = ResetDaysUnit,
            onUnitChange = onUnitChange,
        )
        if (ResetDaysUnit == ResetDaysIntervalUnit.CUSTOM_DAYS) {
            SimpleTextInput(
                label = { Text(stringResource(R.string.IntervalValueSetName)) },
                placeholder = { Text(stringResource(R.string.placeholdertext)) },
                value = ResetDaysValue,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timePickerOverlay(
    isPickerVisible: Boolean,
    hidePicker: () -> Unit,
    onConfirm: (ResetTime) -> Unit
){
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    if (isPickerVisible) {
        Dialog(onDismissRequest = hidePicker) {
            DialogCard {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SettingCardName(
                        text = stringResource(R.string.ResetTimeDialogName),
                        textColor = MaterialTheme.colorScheme.surfaceTint,
                        fontSize = 25.sp
                    )
                    TimePicker(
                        state = timePickerState,
                    )
                    Button(
                        onClick = {
                            onConfirm(
                                ResetTime(
                                    hour = timePickerState.hour,
                                    minute = timePickerState.minute
                                )
                            )
                            hidePicker()
                        }
                    ) {
                        Text(stringResource(R.string.TimePickerOverlayConfirm))
                    }
                }
            }
        }
    }
}