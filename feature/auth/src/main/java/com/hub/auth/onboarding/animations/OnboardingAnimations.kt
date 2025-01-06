package com.hub.auth.onboarding.animations

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.hub.auth.R

@Composable
fun WelcomeAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.welcome_workout))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(300.dp)
    )
}

@Composable
fun WorkoutListAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.workout_list))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(300.dp)
    )
}

@Composable
fun PerformanceAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.performance_chart))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(300.dp)
    )
} 