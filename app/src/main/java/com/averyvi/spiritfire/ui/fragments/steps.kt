package com.averyvi.spiritfire.ui.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.ui.components.ColumnSettingCard
import com.averyvi.spiritfire.ui.components.SettingCardName

@Composable
fun StepsChange(){
    val habitStepsType = remember { mutableStateOf(false) }

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
                    text = stringResource(R.string.StepesCard),
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
            Row() {
                if (habitStepsType.value){
                    // steps selector
                    // steps behaviour
                }
            }
            LazyColumn() {
                if (habitStepsType.value){
                    //step names
                }
            }
        }
    }
}