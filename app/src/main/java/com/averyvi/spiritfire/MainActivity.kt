package com.averyvi.spiritfire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.averyvi.spiritfire.old.data.viewmodels.SingleHabitViewModel
import com.averyvi.spiritfire.old.ui.MainUI
import com.averyvi.spiritfire.old.ui.theme.SpiritfireTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val singleHabitViewModel: SingleHabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiritfireTheme {
                /*MainUI(
                    singleHabitViewModel = singleHabitViewModel
                )*/
            }
        }
    }
}