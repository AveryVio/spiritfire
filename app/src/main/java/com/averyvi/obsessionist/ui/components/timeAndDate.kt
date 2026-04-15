package com.averyvi.obsessionist.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.averyvi.obsessionist.R
import com.averyvi.obsessionist.data.definitions.ResetTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timePicker(
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