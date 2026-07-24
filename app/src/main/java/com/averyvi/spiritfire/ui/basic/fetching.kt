package com.averyvi.spiritfire.ui.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering

@Composable
fun FlowPillButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(256.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            contentColor = color,
            containerColor = color.copy(
                alpha = color.alpha * 0.5f ,
                red = color.red * 0.8f,
                blue = color.blue * 0.8f,
                green = color.green * 0.8f,
            ),
            disabledContentColor = color.copy(
                red = color.red * 0.7f,
                blue = color.blue * 0.7f,
                green = color.green * 0.7f,
            ),
            disabledContainerColor = color.copy(
                alpha = color.alpha * 0.5f,
                red = color.red * 0.7f,
                blue = color.blue * 0.7f,
                green = color.green * 0.7f,
            )
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            content()
        }
    }
}

@Composable
fun HabitSortingFilteringChipsRow(
    sortingFiltering: HabitSortingFiltering,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val sortingVisibility = remember { mutableStateOf(false) }
        val filteringVisibility = remember { mutableStateOf(false) }


        FlowPillButton(
            onClick = {}
        ) {
            Icon(
                painter = painterResource(R.drawable.u_clear_all_24dp_000000_fill0_wght400_grad0_opsz24),
                contentDescription = stringResource(R.string.ClearAll)
            )
        }

        FlowPillButton(
            onClick = {}
        ) {
            Icon(
                painter = painterResource(R.drawable.u_add_24dp_000000_fill0_wght400_grad0_opsz24),
                contentDescription = stringResource(R.string.Add)
            )
        }

        FlowPillButton(
            onClick = { sortingVisibility.value = !sortingVisibility.value }
        ) {
            Icon(
                painter = painterResource(R.drawable.u_sort_24dp_000000_fill0_wght400_grad0_opsz24),
                contentDescription = stringResource(R.string.Sorting)
            )
        }
        AnimatedVisibility(
            visible = sortingVisibility.value
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                sortingFiltering.sorting.forEach {
                    FlowPillButton(
                    ) {
                        Text(stringResource(it.uiText))
                    }
                }
            }
        }

        FlowPillButton(
            onClick = { filteringVisibility.value = !filteringVisibility.value }
        ) {
            Icon(
                painter = painterResource(R.drawable.u_filter_alt_24dp_000000_fill0_wght400_grad0_opsz24),
                contentDescription = stringResource(R.string.Filtering)
            )
        }
        AnimatedVisibility(
            visible = filteringVisibility.value
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                sortingFiltering.filterTypes.forEach {

                    FlowPillButton(
                        onClick = {}
                    ) {
                        Text(stringResource(it.uiText))
                    }
                }
            }
        }
    }
}