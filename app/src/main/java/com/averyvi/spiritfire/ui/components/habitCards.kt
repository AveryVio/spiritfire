package com.averyvi.spiritfire.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.ui.basic.HabitCheckIcon
import com.averyvi.spiritfire.ui.basic.HabitDPTPills
import com.averyvi.spiritfire.ui.basic.IconPillWithValue
import com.averyvi.spiritfire.ui.basic.SmallPill

@Composable
fun BigHabitPropertiesCard(
    habit: HabitRow,
    onCompleteClick: () -> Unit = {},
) {
    UICard() {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    HabitCheckIcon(
                        icon = habit.icon,
                        colour = habit.colour,
                        complete = true,
                        onClick = onCompleteClick,
                        size = 32.dp + 16.dp + 8.dp
                    )
                    Column() {
                        Text(
                            text = habit.name,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = stringResource(habit.resetType.descriptorString),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp + 2.dp))
            HabitDPTPills(
                contractionLevel = 0,
                difficulty = habit.difficulty,
                priority = habit.priority,
                tags = habit.tags,
            )
        }
    }
}

@Composable
fun SmallHabitPropertiesCard(
    habit: HabitRow,
    onCompleteClick: () -> Unit = {},
) {
    UICard() {
        Row(
            modifier = Modifier
                .padding(8.dp + 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HabitCheckIcon(
                    icon = habit.icon,
                    colour = habit.colour,
                    complete = true,
                    onClick = onCompleteClick,
                    size = 32.dp + 16.dp
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(habit.resetType.descriptorString)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            HabitDPTPills(
                contractionLevel = 3,
                difficulty = habit.difficulty,
                priority = habit.priority,
                tags = habit.tags,
            )
        }
    }
}

@Composable
fun MinimalHabitPropertiesCard(
    habit: HabitRow,
    onCompleteClick: () -> Unit = {},
) {
    UICard() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp + 2.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            HabitCheckIcon(
                icon = habit.icon,
                colour = habit.colour,
                complete = true,
                onClick = onCompleteClick,
                size = 32.dp
            )
            Spacer(modifier = Modifier.weight(1f))
            HabitDPTPills(
                contractionLevel = 5,
                difficulty = habit.difficulty,
                priority = habit.priority,
                tags = habit.tags,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}