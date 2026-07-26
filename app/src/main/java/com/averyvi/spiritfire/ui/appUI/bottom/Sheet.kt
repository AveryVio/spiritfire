package com.averyvi.spiritfire.ui.appUI.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.ui.HabitFilterViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering.Companion.inverseFilter
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering.Companion.removeSorting
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering.Companion.reverseSorting
import com.averyvi.spiritfire.ui.basic.SmallPill
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    habitFilterViewModel: HabitFilterViewModel
) {
    val allHabits = habitFilterViewModel.filterItems.collectAsState().value
    val selectedHabits = habitFilterViewModel.selectedHabits.collectAsState().value

    val selectedAllState = when {
        allHabits.all { selectedHabits.contains(it.id) } -> ToggleableState.On
        allHabits.none { selectedHabits.contains(it.id) } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.BottomSheetFiltersTitle).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            },
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        SheetHabitSortingFiltering(
            habitFilterViewModel = habitFilterViewModel
        )
        // filter by tags
        FilterBySpecificHabits(
            habitFilterViewModel = habitFilterViewModel,
            selectedAllState = selectedAllState,
        )

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.u_info_24dp_000000_fill0_wght400_grad0_opsz24),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.BottomSheetInfo),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}

@Composable
fun FilterBySpecificHabits(
    habitFilterViewModel: HabitFilterViewModel,
    selectedAllState: ToggleableState,
) {
    val allHabits = habitFilterViewModel.filterItems.collectAsState().value
    val selectedHabits = habitFilterViewModel.selectedHabits.collectAsState().value

    Row(
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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(190.dp)
    ) {
        allHabits.forEach { currentHabit ->
            item {
                Row(
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
}

@Composable
fun SheetHabitSortingFiltering(
    habitFilterViewModel: HabitFilterViewModel
) {
    val sortingFiltering = habitFilterViewModel.sortingFiltering.collectAsState().value

    Column() {
        Text(
            text = stringResource(R.string.Sorting) + ": ",
            style = MaterialTheme.typography.bodyLarge
        )
        FlowRow(
            itemVerticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            sortingFiltering.sorting.forEachIndexed { index, sort ->
                SmallPill(
                    onClick = {}
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                habitFilterViewModel.updateSortingFiltering(sortingFiltering.reverseSorting(index))
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (sortingFiltering.sortingReverse[index]) R.drawable.u_stat_1_24dp_000000_fill0_wght400_grad0_opsz24
                                    else R.drawable.u_stat_minus_1_24dp_000000_fill0_wght400_grad0_opsz24
                                ),
                                contentDescription = null,
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                        Text(
                            text = stringResource(sort.uiText),

                            )
                    }
                }
            }
            SmallPill(
                onClick = {} // toto add a function to add a sorting
            ) {
                Icon(
                    painter = painterResource(R.drawable.u_add_24dp_000000_fill0_wght400_grad0_opsz24),
                    contentDescription = null
                )
            }
        }

        Text(
            text = stringResource(R.string.Filtering) + ": ",
            style = MaterialTheme.typography.bodyLarge
        )
        FlowRow(
            itemVerticalAlignment = Alignment.CenterVertically
        ) {
            sortingFiltering.filterTypes.forEachIndexed { index, filter ->
                SmallPill(
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                habitFilterViewModel.updateSortingFiltering(sortingFiltering.inverseFilter(index))
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (sortingFiltering.filterInverse[index]) R.drawable.u_stat_1_24dp_000000_fill0_wght400_grad0_opsz24 // todo change icons
                                    else R.drawable.u_stat_minus_1_24dp_000000_fill0_wght400_grad0_opsz24
                                ),
                                contentDescription = null,
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                        Text(
                            text = stringResource(filter.uiText),

                            )
                    }
                }
            }
            SmallPill(
                onClick = {} // toto add a function to add a sorting
            ) {
                Icon(
                    painter = painterResource(R.drawable.u_add_24dp_000000_fill0_wght400_grad0_opsz24),
                    contentDescription = null
                )
            }
        }
    }
}