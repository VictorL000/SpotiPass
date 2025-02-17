package com.victorl000.spotipass.apis.repository

import android.app.Application
import android.bluetooth.BluetoothDevice
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPTransferData
import gupuru.streetpassble.StreetPassBle
import gupuru.streetpassble.parcelable.AdvertiseSuccess
import gupuru.streetpassble.parcelable.DeviceData
import gupuru.streetpassble.parcelable.StreetPassError
import gupuru.streetpassble.parcelable.StreetPassSettings
import gupuru.streetpassble.parcelable.TransferData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BleRepositoryImpl @Inject constructor(
    private val api : BleApi,
    private val appContext : Application,
    private val flow : MutableStateFlow<SPTransferData?>
) : BleRepository{
    override fun bleStart() {
        val responseState = flow.asStateFlow()
        api.bleStart(appContext, flow)
        val appName = appContext.getString(com.victorl000.spotipass.R.string.app_name)
        println(appName)
    }
}