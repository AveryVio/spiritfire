package com.averyvi.spiritfire.old.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.old.data.definitions.HabitColor
import com.averyvi.spiritfire.old.data.definitions.HabitIcon
import com.averyvi.spiritfire.old.data.definitions.ResetDaysIntervalUnit
import com.averyvi.spiritfire.old.data.viewmodels.SingleHabitViewModel

@Composable
fun IconDropdownPicker(
    singleHabitViewModel: SingleHabitViewModel
){
    Box(){
        val selectedIcon = singleHabitViewModel.habit.collectAsState().value.icon
        val cardIsExpanded = remember { mutableStateOf(false) }
        Card(
            onClick = { cardIsExpanded.value = !cardIsExpanded.value },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        ) {
            Icon(
                painter = painterResource(selectedIcon.res),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1.2f)
                    .fillMaxHeight()
                    .padding(8.dp)
            )
        }
        if (cardIsExpanded.value) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { cardIsExpanded.value = false },
                shape = RoundedCornerShape(24.dp),
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ) {
                HabitIcon.entries.forEach { icon ->
                    DropdownMenuItem(
                        leadingIcon = @Composable { Icon(
                            painter = painterResource(icon.res),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null,
                        ) },
                        text = @Composable { Text(
                            text = icon.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            ) },
                        onClick = { singleHabitViewModel.changeHabitValue(newIcon = icon) }
                    )
                }
            }
        }
    }
}

@Composable
fun ColorDropdownPicker(
    singleHabitViewModel: SingleHabitViewModel
){
    Box(){
        val selectedColor = singleHabitViewModel.habit.collectAsState().value.color
        val cardIsExpanded = remember { mutableStateOf(false) }
        Card(
            onClick = { cardIsExpanded.value = !cardIsExpanded.value },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.r_circle),
                tint = selectedColor.color,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1.2f)
                    .fillMaxHeight()
                    .padding(8.dp)
            )
        }
        if (cardIsExpanded.value) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { cardIsExpanded.value = false },
                shape = RoundedCornerShape(24.dp),
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ) {
                HabitColor.entries.forEach { color ->
                    DropdownMenuItem(
                        leadingIcon = @Composable { Icon(
                            painter = painterResource(R.drawable.r_circle),
                            tint = color.color,
                            contentDescription = null,
                        ) },
                        text = @Composable { Text(
                            text = color.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            ) },
                        onClick = { singleHabitViewModel.changeHabitValue(newColor = color) }
                    )
                }
            }
        }
    }
}

@Composable
fun ResetUnitDropdownPicker(
    selectedUnit: ResetDaysIntervalUnit,
    onUnitChange: (ResetDaysIntervalUnit) -> Unit,
){
    Box(){

        val cardIsExpanded = remember { mutableStateOf(false) }
        Card(
            onClick = { cardIsExpanded.value = !cardIsExpanded.value },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(selectedUnit.uiText),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        if (cardIsExpanded.value) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { cardIsExpanded.value = false },
                shape = RoundedCornerShape(24.dp),
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ) {
                ResetDaysIntervalUnit.entries.forEach { unit ->
                    DropdownMenuItem(
                        text = @Composable { Text(
                            text = stringResource(unit.uiText),
                            color = MaterialTheme.colorScheme.onSurface,
                            ) },
                        onClick = { onUnitChange(unit) }
                    )
                }
            }
        }
    }
}