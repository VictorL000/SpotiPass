package com.victorl000.spotipass.apis

import android.app.Application
import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface BleApi {
    fun bleStart(appContext : Application) : StateFlow<SPReceivedData?>
    fun updateBroadcastMessage(newBroadcast : SPTransmittedData)
}