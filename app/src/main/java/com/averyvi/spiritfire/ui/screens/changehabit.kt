package com.averyvi.spiritfire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.averyvi.spiritfire.data.viewmodels.SingleHabitViewModel
import com.averyvi.spiritfire.ui.components.SettingCardName
import com.averyvi.spiritfire.ui.fragments.cards.BasicChange
import com.averyvi.spiritfire.ui.fragments.cards.DateChange
import com.averyvi.spiritfire.ui.fragments.cards.StepsChange

@Composable
fun ChangeHabitScreen(
    singleHabitViewModel: SingleHabitViewModel,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize().padding(
            top = 8.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 4.dp)
    ) {
        SettingCardName( // will make a custom title element
            text = "changehabitt",
            textColor = MaterialTheme.colorScheme.onSurface,
            fontSize = 50.sp
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(350.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item{BasicChange(
                singleHabitViewModel = singleHabitViewModel
            )}
            item{DateChange(
                singleHabitViewModel = singleHabitViewModel
            )}
            item{StepsChange(
                singleHabitViewModel = singleHabitViewModel
            )}
        }
    }
}