package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.TouchBoundsExpansion
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FIcon

@Composable
fun SelectFromDropdown(
    boxModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    expanded: Boolean,
    changeExpansion: (Boolean) -> Unit,
    buttonContents: @Composable () -> Unit,
    dropdownContents: @Composable () -> Unit,

    ) {
    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = { changeExpansion(true) },
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ),
            modifier = buttonModifier
        ) {
            buttonContents()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { changeExpansion(false) }
        ) {
            dropdownContents()
        }
    }
}