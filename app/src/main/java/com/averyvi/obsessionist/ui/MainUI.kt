package com.averyvi.obsessionist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainUI(){
    Scaffold() { innerPadding ->
        Box(){
            Text(
                text = "jgkldfsjgkl",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}