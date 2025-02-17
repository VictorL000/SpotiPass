package com.victorl000.spotipass.apis.repository

import android.app.Application
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class BleRepositoryImpl @Inject constructor(
    private val api : BleApi,
    private val appContext : Application,
    private val flow : MutableStateFlow<SPReceivedData?>,
    private val profileFlow : MutableStateFlow<SPProfile>,
) : BleRepository{
    override fun bleStart() {
//        val responseState = flow.asStateFlow()
        api.bleStart(appContext, flow)
//        val appName = appContext.getString(com.victorl000.spotipass.R.string.app_name)
//        println(appName)
    }

    override fun updateBroadcastMessage(newBroadcast : SPTransmittedData) {
        api.updateBroadcastMessage(newBroadcast)
    }
}