package com.victorl000.spotipass.ui.homeview.discover

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPReceivedData
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
    private val flow: MutableStateFlow<List<SPReceivedData>>,
    private val transferFlow: MutableStateFlow<SPReceivedData?>,
    @ApplicationContext
    private val context: Context
) : ViewModel() {

    fun updateValue(newValue: SPReceivedData) {
        flow.value += newValue
    }
    init {
        viewModelScope.launch {
            transferFlow.collect {
                it?.let {
                    updateValue(it)
                }
            }
        }
    }


    fun observeDiscoverListFlow(): StateFlow<List<SPReceivedData>> = flow.asStateFlow()
}
