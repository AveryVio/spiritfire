package com.averyvi.spiritfire.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.ResetDaysInterval
import com.averyvi.spiritfire.data.definitions.ResetTime
import java.util.Calendar

@Composable
fun timePicker(

) {
    Row() {
        Card() { Text("15") }
        Text(":")
        Card() { Text("30") }
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
                            onConfirm( ResetTime(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute
                            ))
                            hidePicker()
                        }
                    ) {
                        Text("jfklsdfj")
                    }
                }
            }
        }
    }
}
@Composable
fun datePicker(
    isPickerVisible: Boolean,
    hidePicker: () -> Unit,
    onConfirm: (ResetDaysInterval) -> Unit
) {
}