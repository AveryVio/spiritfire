package com.averyvi.spiritfire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.averyvi.spiritfire.data.viewmodels.SingleHabitViewModel
import com.averyvi.spiritfire.ui.theme.SpiritfireTheme
import com.averyvi.spiritfire.ui.MainUI

class MainActivity : ComponentActivity() {
    private val singleHabitViewModel: SingleHabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiritfireTheme {
                MainUI(
                    singleHabitViewModel = singleHabitViewModel
                )
            }
        }
    }
}

/*
language guides
single habit: flame
all habits: pyre
*/

/*
ui language
flame cards view
big screen flame view
pyre cards
settings
campfire (pill)
*/