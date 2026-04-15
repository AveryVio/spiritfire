package com.averyvi.obsessionist.ui.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.obsessionist.R
import com.averyvi.obsessionist.data.definitions.ResetTime
import com.averyvi.obsessionist.ui.components.ColorDropdownPicker
import com.averyvi.obsessionist.ui.components.ColumnSettingCard
import com.averyvi.obsessionist.ui.components.IconDropdownPicker
import com.averyvi.obsessionist.ui.components.SettingCardName
import com.averyvi.obsessionist.ui.components.timePicker
import com.averyvi.obsessionist.ui.elements.ObsTextInput
import dalvik.system.InMemoryDexClassLoader
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.sin

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
                ObsTextInput(
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
            ObsTextInput(
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

    var daysResetInterval: Long
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
            //reset time
            Button(onClick = { isTimePickerVisible.value = true }) {
                Text("Show Overlay")
            }
            timePicker(
                isPickerVisible = isTimePickerVisible.value,
                hidePicker = { isTimePickerVisible.value = false },
                onConfirm = {
                    resetTime.value = it
                }
            )
        }
    }

}

@Composable
fun PrioritySortingChange(){
    //priority
}