package com.victorl000.spotipass.ui.homeview.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorl000.spotipass.domain.repository.SpotifyRepository
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.domain.repository.CryptoRepository
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
    private val repository: BleRepository,
    private val profileFlow: MutableStateFlow<SPProfile?>,
    // Why are we doing this? We should only store profile uuid.
    // second thought, maybe it's ok since we want to have this data on hand and not query it (i think)
    private val cryptoRepository: CryptoRepository,
    private val spotifyRepository: SpotifyRepository,
) : ViewModel() {
    fun startBLEService() {
        repository.bleStart()
    }
    fun updateValue(newValue: SPProfile) {
        profileFlow.value = newValue
    }

    init {
        viewModelScope.launch {
            profileFlow.collect {
                it?.let {
                    repository.updateBroadcastMessage(
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
            spotifyRepository.refreshToken().also {
                cryptoRepository.saveTokens(it.access_token, it.refresh_token)
            }
        }
    }

    fun observeProfileFlow(): StateFlow<SPProfile?> = profileFlow.asStateFlow()
}
