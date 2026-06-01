package com.averyvi.spiritfire.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.ColumnInfo
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.ResetDays
import com.averyvi.spiritfire.data.definitions.habits.ResetDaysType
import com.averyvi.spiritfire.data.definitions.habits.checkTypes
import com.averyvi.spiritfire.ui.bottom.AppBottomSheet
import com.averyvi.spiritfire.ui.bottom.NavPill
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.concurrent.thread

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    habitDAO: HabitRegistryUserDao
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        // 2. Set the peek height to match the navigation bar
        sheetPeekHeight = 80.dp,
        sheetContent = {
            // The sheet content is a column where the top element is your Nav Bar
            Column(modifier = Modifier.fillMaxWidth()) {

                // --- Top Section: Custom Bottom Navigation ---
                // This remains visible at the bottom of the screen when collapsed
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
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
        }
    ) { innerPadding ->

        // Main Screen Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                // 3. Apply the innerPadding to your main content
                .padding(innerPadding)
        ) {
            Text(
                text = "Main Map or App Content goes here.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    /*BottomSheetScaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        topBar = {
        },
        sheetPeekHeight = 64.dp,
        sheetContent = @Composable {
            AppBottomSheet()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ){
            val navController = rememberNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            NavHost(
                navController = navController,
                startDestination = Routes.HabitOverview.name,
            ) {

                val onRouteButtonClicked = { route: Routes ->
                    navController.navigate(route = route.name)
                }

                composable(route = Routes.HabitOverview.name) {
                    Text("fjsklfdj")
                }
            }

            testinggui()
        }
    }*/
        //testingdb(habitDAO = habitDAO)
}