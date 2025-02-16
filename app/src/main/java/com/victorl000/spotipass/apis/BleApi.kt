package com.victorl000.spotipass.apis

import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow

interface BleApi {
    fun bleStart(appContext : Application, flow : MutableStateFlow<String>)
}