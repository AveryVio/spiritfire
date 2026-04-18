package com.averyvi.spiritfire.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.HabitColor
import com.averyvi.spiritfire.data.definitions.HabitIcon
import com.averyvi.spiritfire.data.definitions.ResetDaysIntervalUnit

@Composable
fun IconDropdownPicker(){
    Box(){
        val selectedIcon = remember { mutableStateOf(HabitIcon.moon) }
        val cardIsExpanded = remember { mutableStateOf(false) }
        Card(
            onClick = { cardIsExpanded.value = !cardIsExpanded.value },
            shape = RoundedCornerShape(24.dp),
        ) {
            Icon(
                painter = painterResource(selectedIcon.value.res),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.aspectRatio(1.2f).fillMaxHeight().padding(8.dp)
            )
        }
        if (cardIsExpanded.value) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { cardIsExpanded.value = false },
                shape = RoundedCornerShape(24.dp),
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
                        onClick = { selectedIcon.value = icon }
                    )
                }
            }
        }
    }
}

@Composable
fun ColorDropdownPicker(){
    Box(){
        val selectedColor = remember { mutableStateOf(HabitColor.Purple) }
        val cardIsExpanded = remember { mutableStateOf(false) }
        Card(
            onClick = { cardIsExpanded.value = !cardIsExpanded.value },
            shape = RoundedCornerShape(24.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.r_circle),
                tint = selectedColor.value.color,
                contentDescription = null,
                modifier = Modifier.aspectRatio(1.2f).fillMaxHeight().padding(8.dp)
            )
        }
        if (cardIsExpanded.value) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { cardIsExpanded.value = false },
                shape = RoundedCornerShape(24.dp),
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
                        onClick = { selectedColor.value = color }
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