package com.averyvi.spiritfire.ui.appUI.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TriStateCheckbox
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
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.capitalize
import com.averyvi.spiritfire.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    habitFilterViewModel: HabitFilterViewModel
) {
    val allHabits = habitFilterViewModel.filterItems.collectAsState().value
    val selectedHabits = habitFilterViewModel.selectedFilters.collectAsState().value

    val selectedAllState = when {
        allHabits.all { selectedHabits.contains(it.id) } -> ToggleableState.On
        allHabits.none { selectedHabits.contains(it.id) } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(
        modifier = Modifier.padding(horizontal = 4.dp + 2.dp)
    ) {
        Text(
            text = stringResource(R.string.BottomSheetFiltersTitle).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            },
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp + 8.dp)
        )
        Spacer(
            modifier = Modifier.height(16.dp + 4.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TriStateCheckbox(
                state = selectedAllState,
                onClick = {
                    if (allHabits.size == selectedHabits.size) {
                        allHabits.forEach { habitFilterViewModel.unselectFilter(it.id) }
                    } else {
                        allHabits.forEach { habitFilterViewModel.selectFilter(it.id) }
                    }
                },
                colors = CheckboxDefaults.colors().copy(
                    checkedCheckmarkColor = MaterialTheme.colorScheme.surface,
                    checkedBoxColor = MaterialTheme.colorScheme.onSurface,
                    checkedBorderColor = MaterialTheme.colorScheme.onSurface,

                    )
            )
            Text(
                text = stringResource(R.string.SelectAll).replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            )
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp)
        ) {
            allHabits.forEach { currentHabit ->
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Checkbox(
                            checked = selectedHabits.contains(currentHabit.id),
                            onCheckedChange = {
                                habitFilterViewModel.toggleFilter(
                                    currentHabit.id
                                )
                            },
                            colors = CheckboxDefaults.colors().copy(
                                checkedBoxColor = Color(currentHabit.colour),
                                checkedBorderColor = Color(currentHabit.colour),
                            ),
                        )
                        Text(
                            text = currentHabit.name
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 16.dp + 8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.u_info_24dp_000000_fill0_wght400_grad0_opsz24),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = "info icon"
            )
            Text(
                text = stringResource(R.string.BottomSheetInfo),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(
            modifier = Modifier.height(16.dp + 8.dp)
        )
    }
}