package com.averyvi.spiritfire.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.R
import com.averyvi.spiritfire.data.definitions.habits.FColour
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.ui.basic.CircularHabitProgress
import com.averyvi.spiritfire.ui.basic.LinearIconifiedProgress
import kotlinx.coroutines.flow.Flow

@Composable
fun UICard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
    ) {
        Box() {
            content()
        }
    }
}

@Composable
fun WideHabitOverviewCard(
    habit: HabitRow,
    completeCount: Int,
) {
    val progress = completeCount / habit.checksAmount

    UICard() {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularHabitProgress(
                        icon = habit.icon,
                        colour = habit.colour,
                        complete = completeCount >= habit.checksComplete,
                        progress = progress.toFloat(),
                        size = 32.dp
                    )
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = stringResource(habit.resetType.uiText)
                )
            }
            Spacer(modifier = Modifier.height(8.dp + 2.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LinearIconifiedProgress(
                    iconCount = 5,
                    icon = R.drawable.ur_mode_heat_24dp_000000_fill0_wght400_grad0_opsz24,
                    value = habit.difficulty,
                    colour = FColour.Red.color,
                    size = 16.dp + 8.dp
                )
                LinearIconifiedProgress(
                    iconCount = 7,
                    icon = R.drawable.ur_star_24dp_000000_fill0_wght400_grad0_opsz24,
                    value = habit.priority,
                    colour = FColour.Yellow.color,
                    size = 16.dp + 8.dp
                )
            }
            Spacer(Modifier.height(4.dp + 2.dp))
            HorizontalDivider(
                thickness = 2.dp
            )
            Spacer(Modifier.height(4.dp + 2.dp))
            Row() {
                // streak
                // last few checks
            }
        }
    }
}