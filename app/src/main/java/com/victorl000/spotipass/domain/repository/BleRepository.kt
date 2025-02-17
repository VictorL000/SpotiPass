package com.victorl000.spotipass.domain.repository

import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData

interface BleRepository {
    fun bleStart()
    fun updateBroadcastMessage(newBroadcast : SPTransmittedData)
}