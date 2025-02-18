package com.victorl000.spotipass.ui.homeview.discover

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.ui.loginview.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeDiscoverViewModel @Inject constructor(
    private val repository: BleRepository,
    @ApplicationContext
    private val context: Context
) : ViewModel() {
    private val _discoverListFlow = MutableStateFlow<List<SPReceivedData>>(listOf())

    fun updateValue(newValue: SPReceivedData) {
        _discoverListFlow.value += newValue
    }

    init {
        viewModelScope.launch {
            // TODO: the viewmodel should be in charge of storing the list of discovered users.
            repository.bleStart().collect {
                it?.let {
                    updateValue(it)
                }
            }
        }
    }

    fun observeDiscoverListFlow(): StateFlow<List<SPReceivedData>> = _discoverListFlow.asStateFlow()
}
