package com.averyvi.spiritfire.old.ui.fragments.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.old.data.viewmodels.SingleHabitViewModel
import com.averyvi.spiritfire.old.ui.components.ColumnSettingCard
import com.averyvi.spiritfire.old.ui.components.SettingCardName
import com.averyvi.spiritfire.old.ui.components.datePicker
import com.averyvi.spiritfire.old.ui.components.timePicker

@Composable
fun DateChange(
    singleHabitViewModel: SingleHabitViewModel,
){
    val selectedUnit = singleHabitViewModel.habit.collectAsState().value.resetInterval.interval_unit
    val resetDaysValue = singleHabitViewModel.habit.collectAsState().value.resetInterval.interval_value
    val resetTime = singleHabitViewModel.habit.collectAsState().value.resetTime

    ColumnSettingCard {
        Column() {
            SettingCardName(
                text = stringResource(R.string.HabitTimeChangeCard),
                textColor = MaterialTheme.colorScheme.secondary,
                fontSize = 27.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(56.dp).fillMaxWidth()
            ) {
                timePicker(
                    resetTime = resetTime,
                    onResetTime = {
                        singleHabitViewModel.changeHabitValue(newResetTime = it)
                    },
                    modifier = Modifier.weight(1f)
                )
                datePicker(
                    ResetDaysUnit = selectedUnit,
                    onUnitChange = { singleHabitViewModel.changeHabitValue(newResetIntervalUnit = it) },
                    ResetDaysValue = resetDaysValue,
                    onValueChange = {
                        if (it.length < 30) {
                            singleHabitViewModel.changeHabitValue(newResetIntervalValue = it.filter { numb -> numb.isDigit() })
                        }
                    },
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            }
        }
    }

}

@Composable
fun PrioritySortingChange(){
    //priority
}