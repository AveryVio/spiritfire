package com.averyvi.spiritfire.data.definitions

import androidx.compose.ui.graphics.Color

enum class HabitColor(val color: Color){
    Red(color = Color.hsv(0f, 0.55f, 1f)),
    Green(color = Color.hsv(120f, 0.55f, 0.8f)),
    Blue(color = Color.hsv(224f, 0.55f, 1f)),
    Yellow(color = Color.hsv(50f, 0.5f, 0.9f)),
    Purple(color = Color.hsv(256f, 0.66f, 1f)),
}