package com.victorl000.spotipass.ui.profileview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorl000.spotipass.domain.repository.SpotifyRepository
import com.victorl000.spotipass.domain.repository.UserProfileRepository
import com.victorl000.spotipass.model.SPProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface OtherUserProfileUiState {
    data object Loading : OtherUserProfileUiState
    data class Success(val profile: SPProfile) : OtherUserProfileUiState
    data class Error(val message: String?) : OtherUserProfileUiState
}

@HiltViewModel
class OtherUserProfileViewModel @Inject constructor(
    private val spotifyRepository: SpotifyRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<OtherUserProfileUiState>(OtherUserProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadUserProfile(userId: String) {
        _uiState.value = OtherUserProfileUiState.Loading
        viewModelScope.launch {
            try {
                val profile = userProfileRepository.getProfile(userId)
                _uiState.value = OtherUserProfileUiState.Success(profile)
            } catch (e: Exception) {
                _uiState.value =
                    OtherUserProfileUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}