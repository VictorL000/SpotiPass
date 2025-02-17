package com.victorl000.spotipass.ui.homeview.profile

import androidx.lifecycle.ViewModel
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPTransferData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeProfileViewModel @Inject constructor(
    private val repository: BleRepository,
    private val flow: MutableStateFlow<SPTransferData?>
) : ViewModel() {
    fun startBLEService() {
        repository.bleStart()
    }
    fun updateValue(newValue: SPTransferData?) {
        flow.value = newValue
    }

    fun observeFlow(): StateFlow<SPTransferData?> = flow.asStateFlow()
}
