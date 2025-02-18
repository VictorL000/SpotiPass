package com.victorl000.spotipass.ui.homeview.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorl000.spotipass.domain.repository.SpotifyRepository
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.domain.repository.CryptoRepository
import com.victorl000.spotipass.domain.repository.ProfileRepository
import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.model.SPTransmittedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeProfileViewModel @Inject constructor(
    private val profileRepo: ProfileRepository,
//    private val profileFlow: MutableStateFlow<SPProfile?>,
    // Why are we doing this? We should only store profile uuid.
    // second thought, maybe it's ok since we want to have this data on hand and not query it (i think)
    private val cryptoRepository: CryptoRepository,
    private val spotifyRepository: SpotifyRepository,
    private val bleRepository: BleRepository,
) : ViewModel() {
    private val _profileState = MutableStateFlow<SPProfile>(getCurrentProfile())
    val profileState: StateFlow<SPProfile> = _profileState

    fun updateValue(newValue: SPProfile) {
        profileRepo.setCurrentProfile(newValue)
        _profileState.value = profileRepo.getCurrentProfile()
    }
//
    init {
        viewModelScope.launch {
            profileState.collect {
                it.let {
                    Log.d(TAG, "collected profile change!")
                    bleRepository.updateBroadcastMessage(
                        SPTransmittedData(
                            it.profileId,
                            it.username,
                            it.spotifyUserId,
                            it.spotifyUrl,
                        )
                    )
                }
            }
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            spotifyRepository.refreshToken()
            /*.also {
                cryptoRepository.saveTokens(it.access_token, it.refresh_token)
            }*/
        }
    }
    fun getCurrentProfile() = profileRepo.getCurrentProfile()

//    fun observeProfileFlow(): StateFlow<SPProfile?> = profileRepo.asStateFlow()
}
