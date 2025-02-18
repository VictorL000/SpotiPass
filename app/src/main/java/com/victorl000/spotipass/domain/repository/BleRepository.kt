package com.victorl000.spotipass.domain.repository

import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface BleRepository {
    fun bleStart() : StateFlow<SPReceivedData?>
    fun updateBroadcastMessage(newBroadcast : SPTransmittedData)
}