package com.averyvi.spiritfire.ui.appUI.top

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.averyvi.spiritfire.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(

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