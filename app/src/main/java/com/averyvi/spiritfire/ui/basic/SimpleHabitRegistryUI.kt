package com.averyvi.spiritfire.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.TagUIEntity

@Composable
fun HabitDPTPills(
    contractionLevel: Int,
    difficulty: Int,
    priority: Int,
    tags: List<TagUIEntity>
) {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (contractionLevel < 5) {
            IconPillWithValue(
                icon = R.drawable.ur_mode_heat_24dp_000000_fill0_wght400_grad0_opsz24,
                value = difficulty,
                colour = FColour.Red.color,
            )
            IconPillWithValue(
                icon = R.drawable.ur_star_24dp_000000_fill0_wght400_grad0_opsz24,
                value = priority,
                colour = FColour.Yellow.color,
            )
        }
        if(contractionLevel < 2) {
            tags.forEachIndexed { index, entity ->
                if(contractionLevel == 0) {
                    if (index < 7) {
                        SmallPill(
                            color = Color(entity.colour)
                        ) {
                            Text(
                                text = entity.name
                            )
                        }
                    } else if (index == 7) {
                        SmallPill(
                            color = MaterialTheme.colorScheme.onSurface
                        ) {
                            Icon(
                                painterResource(R.drawable.u_more_horiz_24dp_000000_fill0_wght400_grad0_opsz24),
                                contentDescription = null,
                            )
                        }
                    }
                } else {
                    if (index < 3) {
                        SmallPill(
                            color = Color(entity.colour)
                        ) {
                            Text(
                                text = entity.name
                            )
                        }
                    } else if (index == 3) {
                        SmallPill(
                            color = MaterialTheme.colorScheme.onSurface
                        ) {
                            Icon(
                                painterResource(R.drawable.u_more_horiz_24dp_000000_fill0_wght400_grad0_opsz24),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        } else {
            IconPillWithValue(
                icon = R.drawable.u_tag_24dp_000000_fill0_wght400_grad0_opsz24,
                value = tags.size,
                colour = MaterialTheme.colorScheme.primary,
            )
        }
    }
}