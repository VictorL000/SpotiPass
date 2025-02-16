package com.victorl000.spotipass.ui.homeview

import androidx.lifecycle.ViewModel
import com.victorl000.spotipass.domain.repository.BleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BleRepository
) : ViewModel() {
    init {
        println("HOME INITIALIZED")
    }
    fun say() {
        repository.bleStart()
    }
}