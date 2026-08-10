package com.averyvi.spiritfire.ui.appUI.top

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.ui.ChartData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralAppBar(

){
    CenterAlignedTopAppBar(
        title = @Composable { Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall.copy(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.secondary,
                    )
                )
            ),
            fontWeight = FontWeight.Black,
        ) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        ),
        navigationIcon = @Composable {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.u_menu_24dp_000000_fill0_wght400_grad0_opsz24),
                    contentDescription = null //
                )
            }
        },
        actions = @Composable {
            Row() {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.u_hdr_strong_24dp_000000_fill0_wght400_grad0_opsz24),
                        contentDescription = null // more apps ig
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.u_settings_24dp_000000_fill0_wght400_grad0_opsz24),
                        contentDescription = stringResource(R.string.settingsScreen)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitAppBar(
    habitRow: HabitRow,
    data: List<ChartData>,
    onNameClick: () -> Unit,
){
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
        ) {

            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {

            }

        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    }
    CenterAlignedTopAppBar(
        title = @Composable {
            Text(
                text = habitRow.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = onNameClick
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        ),
        navigationIcon = @Composable {
            Icon(
                painter = painterResource(habitRow.icon),
                modifier = Modifier
                    .size(48.dp),
                tint = habitRow.colour,
                contentDescription = null,
            )
        },
        actions = @Composable {
            Row() {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(64.dp + 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ur_edit_24dp_000000_fill0_wght400_grad0_opsz24),
                        contentDescription = stringResource(R.string.Edit)
                    )
                }
            }
        }
    )
}