package com.averyvi.spiritfire.ui.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {

        // --- Top Section: Custom Bottom Navigation ---
        // This remains visible at the bottom of the screen when collapsed
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { /* Action */ }) { Text("Home") }

            // You could programmatically expand the sheet here if desired
            TextButton(onClick = { /* Action */ }) { Text("Menu") }

            TextButton(onClick = { /* Action */ }) { Text("Profile") }
        }

        // --- Bottom Section: The Hidden Sheet Content ---
        // This sits below the screen edge and slides up smoothly
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // Ensure it has enough height to look like a full menu when expanded
                .height(400.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "Expanded Menu Content",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Because the navigation bar is at the top of this layout, pulling up on the navigation bar seamlessly reveals this content underneath it.")
        }
    }
/*
    Column(modifier = Modifier.fillMaxWidth()) {
        //Pill
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavPill(changeSheetVisibility = { })
        }

        // Sheet content
        Button(onClick = { }) {
            Text("Hide bottom sheet")
        }
        Text("Hide bottom sheet"); Text("Hide bottom sheet"); Text("Hide bottom sheet"); Text("Hide bottom sheet"); Text(
        "Hide bottom sheet"
    ); Text("Hide bottom sheet"); Text("Hide bottom sheet"); Text("Hide bottom sheet"); Text("Hide bottom sheet"); Text(
        "Hide bottom sheet"
    );
    }*/
}