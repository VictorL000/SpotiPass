package com.victorl000.spotipass.apis

import android.app.Application
import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData
import kotlinx.coroutines.flow.MutableStateFlow

interface BleApi {
    fun bleStart(appContext : Application, flow : MutableStateFlow<SPReceivedData?>)
    fun updateBroadcastMessage(newBroadcast : SPTransmittedData)
}