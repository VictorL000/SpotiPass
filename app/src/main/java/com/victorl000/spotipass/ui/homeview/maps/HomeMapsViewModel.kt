package com.victorl000.spotipass.ui.homeview.maps

import androidx.lifecycle.ViewModel
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPReceivedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeMapsViewModel @Inject constructor(
    private val repository: BleRepository,
    private val flow: MutableStateFlow<SPReceivedData?>
) : ViewModel() {
    fun startBLEService() {
        repository.bleStart()
    }
    fun updateValue(newValue: SPReceivedData?) {
        flow.value = newValue
    }

    fun observeFlow(): StateFlow<SPReceivedData?> = flow.asStateFlow()
}
