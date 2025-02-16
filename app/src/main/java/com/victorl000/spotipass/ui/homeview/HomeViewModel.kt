package com.victorl000.spotipass.ui.homeview

import androidx.lifecycle.ViewModel
import com.victorl000.spotipass.domain.repository.BleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BleRepository,
    private val flow: MutableStateFlow<String>
) : ViewModel() {
    init {
        println("HOME INITIALIZED")
    }
    fun say() {
        repository.bleStart()
    }
    fun updateValue(newValue: String) {
        flow.value = newValue
    }

    fun observeFlow(): StateFlow<String> = flow.asStateFlow()
}