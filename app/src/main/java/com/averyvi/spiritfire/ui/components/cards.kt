package com.averyvi.spiritfire.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UICard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Card(modifier = modifier) {
        Box() {
            content()
        }
    }
}