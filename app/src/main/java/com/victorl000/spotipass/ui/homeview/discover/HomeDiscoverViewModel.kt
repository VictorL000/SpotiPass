package com.victorl000.spotipass.ui.homeview.discover

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPTransferData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeDiscoverViewModel @Inject constructor(
    private val repository: BleRepository,
    private val flow: MutableStateFlow<List<SPTransferData>>,
    private val transferFlow: MutableStateFlow<SPTransferData?>,
    @ApplicationContext
    private val context: Context
) : ViewModel() {

    fun updateValue(newValue: SPTransferData) {
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


    fun observeDiscoverListFlow(): StateFlow<List<SPTransferData>> = flow.asStateFlow()
}
