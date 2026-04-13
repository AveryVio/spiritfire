package com.averyvi.obsessionist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.obsessionist.R
import com.averyvi.obsessionist.data.definitions.ObsColor
import com.averyvi.obsessionist.data.definitions.ObsIcon

@Composable
fun IconDropdownPicker(){
    Box(){
        val selectedIcon = remember { mutableStateOf(ObsIcon.moon) }
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
                ObsIcon.entries.forEach { icon ->
                    DropdownMenuItem(
                        leadingIcon = @Composable { Icon(
                            painter = painterResource(icon.res),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null,
                        ) },
                        text = @Composable { Text(icon.name) },
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
        val selectedColor = remember { mutableStateOf(ObsColor.Purple) }
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
                ObsColor.entries.forEach { color ->
                    DropdownMenuItem(
                        leadingIcon = @Composable { Icon(
                            painter = painterResource(R.drawable.r_circle),
                            tint = color.color,
                            contentDescription = null,
                        ) },
                        text = @Composable { Text(color.name) },
                        onClick = { selectedColor.value = color }
                    )
                }
            }
        }
    }
}