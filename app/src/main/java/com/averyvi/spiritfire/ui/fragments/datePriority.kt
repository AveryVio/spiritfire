package com.averyvi.spiritfire.ui.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.ResetDaysInterval
import com.averyvi.spiritfire.data.definitions.ResetDaysIntervalUnit
import com.averyvi.spiritfire.data.definitions.ResetTime
import com.averyvi.spiritfire.ui.components.ColumnSettingCard
import com.averyvi.spiritfire.ui.components.SettingCardName
import com.averyvi.spiritfire.ui.components.datePicker
import com.averyvi.spiritfire.ui.components.timePicker

@Composable
fun DateChange(){
    var daysResetInterval: ResetDaysInterval
    val selectedUnit = remember { mutableStateOf(ResetDaysIntervalUnit.DAILY) }
    val resetDaysValue = remember { mutableStateOf("") }
    val resetTime = remember { mutableStateOf(
        ResetTime(
            hour = 0,
            minute = 0
        )
    ) }

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
                modifier = Modifier.Companion.height(56.dp).fillMaxWidth()
            ) {
                timePicker(
                    resetTime = resetTime.value,
                    onResetTime = {
                        resetTime.value = it
                    },
                    modifier = Modifier.Companion.weight(1f)
                )
                datePicker(
                    ResetDaysUnit = selectedUnit.value,
                    onUnitChange = { selectedUnit.value = it },
                    ResetDaysValue = resetDaysValue.value,
                    onValueChange = {
                        if (it.length < 30) {
                            resetDaysValue.value = it.filter { numb -> numb.isDigit() }
                        }
                    },
                    modifier = Modifier.Companion.fillMaxWidth().weight(1f)
                )
            }
        }
    }

}

@Composable
fun PrioritySortingChange(){
    //priority
}