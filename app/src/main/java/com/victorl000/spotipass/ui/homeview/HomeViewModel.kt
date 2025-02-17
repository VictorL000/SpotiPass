package com.victorl000.spotipass.ui.homeview

import androidx.lifecycle.ViewModel
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPReceivedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BleRepository,
    private val flow: MutableStateFlow<SPReceivedData?>
) : ViewModel() {
    init {
        startBLEService()
    }
    fun startBLEService() {
        repository.bleStart()
    }
//    fun updateValue(newValue: SPTransferData?) {
//        flow.value = newValue
//    }
//
//    fun observeFlow(): StateFlow<SPTransferData?> = flow.asStateFlow()
}