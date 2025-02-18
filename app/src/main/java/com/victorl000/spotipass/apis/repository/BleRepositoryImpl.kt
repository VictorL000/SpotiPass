package com.victorl000.spotipass.apis.repository

import android.app.Application
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BleRepositoryImpl @Inject constructor(
    private val api : BleApi,
    private val appContext : Application,
) : BleRepository{
    override fun bleStart() : StateFlow<SPReceivedData?> {
//        val responseState = flow.asStateFlow()
        return api.bleStart(appContext)
//        val appName = appContext.getString(com.victorl000.spotipass.R.string.app_name)
    }

    override fun updateBroadcastMessage(newBroadcast : SPTransmittedData) {
        api.updateBroadcastMessage(newBroadcast)
    }
}