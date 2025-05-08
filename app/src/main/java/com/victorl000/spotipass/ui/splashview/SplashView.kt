package com.victorl000.spotipass.ui.splashview

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue

@Composable
fun SplashView(
    viewModel: SplashViewModel,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            is SplashViewModel.SplashState.GoToHome -> {
                Log.d("splash", "navigating to home")
                navigateToHome()
            }
            is SplashViewModel.SplashState.GoToLogin -> {
                Log.d("splash", "navigating to login")
                navigateToLogin()
            }
            else -> {
                Log.d("splash", "else")
            } // Loading
        }
    }

    // Optional loading indicator
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

