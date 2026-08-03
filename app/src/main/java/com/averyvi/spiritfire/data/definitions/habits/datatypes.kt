package com.averyvi.spiritfire.data.definitions.habits

import androidx.compose.ui.graphics.Color
import com.averyvi.spiritfire.R

enum class FColour(val color: Color){
    Red(color = Color.hsv(0f, 0.55f, 1f)),
    Green(color = Color.hsv(120f, 0.55f, 0.8f)),
    Blue(color = Color.hsv(224f, 0.55f, 1f)),
    Yellow(color = Color.hsv(50f, 0.5f, 0.9f)),
    Purple(color = Color.hsv(256f, 0.66f, 1f)),
}

enum class SColour(val color: Color){
    Grey(color = Color.hsv(0f, 0f, 0.32f )),
    JaOrange(color = Color.hsv(24f, 0.24f, 0.54f )),
}

enum class FIcon( val res: Int, ){
    moon(res = R.drawable.r_outline_dark_mode_2),
    circle(res = R.drawable.r_circle),
    battery(res = R.drawable.r_outline_battery_android_0_24),
    flowey(res = R.drawable.r_outline_deceased_24),
    water(res = R.drawable.r_outline_humidity_low_24),
    monitor(res = R.drawable.r_outline_jamboard_kiosk_24),
    music(res = R.drawable.r_outline_music_note_24),
    pill(res = R.drawable.r_outline_pill_24),
    fire(res = R.drawable.ur_mode_heat_24dp_000000_fill0_wght400_grad0_opsz24),
    star(res = R.drawable.ur_star_24dp_000000_fill0_wght400_grad0_opsz24),
}

enum class ResetDaysType(
    val descriptorString: Int,
) {
    DAILY(
        descriptorString = R.string.DailyChange,
    ),
    WEEKLY(
        descriptorString = R.string.WeekklyChange,
    ),
    MONTHLY(
        descriptorString = R.string.MonthlyChange,
    ),
    YEARLY(
        descriptorString = R.string.YearlyChange,
    ),
    CUSTOM_DAYS(
        descriptorString = R.string.CustomDaysChange,
    ),
}

data class ResetTime(
    val hour: Int,
    val minute: Int
) {
    companion object {
        val MIDNIGHT = ResetTime(
            hour = 0,
            minute = 0
        )
        val MIDDAY = ResetTime(
            hour = 12,
            minute = 0
        )
        val LASTMINUTE = ResetTime(
            hour = 23,
            minute = 59
        )
    }
}

enum class checkTypes(
    val stringRef: Int
) {
    COMPLETIONS(stringRef = R.string.ChecksCompletions),
    STEPS(stringRef = R.string.ChecksSteps),
}

