package com.hub.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalCustomColors = staticCompositionLocalOf {
    WorkoutColors()
}

object WorkoutTheme {
    val colors: WorkoutColors
        @Composable
        get() = LocalCustomColors.current
}

@Composable
fun WorkoutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) {
        WorkoutColors(
            opacityTextColor = OpacityDark,
            iconTint = Color.White,
            bottomSheetBackground = Dark,
            bottomSheetCardBackground = CardBackground,
            textColor = Color.White,
            textColorReverse = Color.Black,
            backgroundColor = Dark,
            cardBackground = CardBackground,
            primaryColor = Primary,
            secondaryColor = Primary200,
            captionColor = Caption,
            borderAndLightBorder = Border
        )
    } else {
        WorkoutColors(
            opacityTextColor = OpacityLight,
            iconTint = Color.Black,
            bottomSheetBackground = Light,
            bottomSheetCardBackground = Grey00,
            textColor = Color.Black,
            textColorReverse = Color.White,
            backgroundColor = Light,
            cardBackground = Color.White,
            primaryColor = Primary,
            secondaryColor = Primary200,
            captionColor = Caption,
            borderAndLightBorder = Grey00
        )
    }

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        content = content
    )
}
