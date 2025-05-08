package com.victorl000.spotipass.ui.splashview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorl000.spotipass.domain.repository.CryptoRepository
import com.victorl000.spotipass.domain.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: SpotifyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashState>(SplashState.Loading)
    val uiState: StateFlow<SplashState> = _uiState

    init {
        viewModelScope.launch {
            var isLoggedIn = false
            try{
                isLoggedIn = authRepository.refreshToken().access_token.isNotBlank()
            } catch(e: Error) {}
            _uiState.value = if (isLoggedIn) SplashState.GoToHome else SplashState.GoToLogin
        }
    }

    sealed class SplashState {
        object Loading : SplashState()
        object GoToHome : SplashState()
        object GoToLogin : SplashState()
    }
}

