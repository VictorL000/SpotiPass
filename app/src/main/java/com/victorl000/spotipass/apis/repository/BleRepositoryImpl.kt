package com.victorl000.spotipass.apis.repository

import android.app.Application
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.domain.repository.BleRepository
import javax.inject.Inject

class BleRepositoryImpl @Inject constructor(
    private val api : BleApi,
    private val appContext : Application
) : BleRepository{
    override fun bleStart() {
        api.bleStart()
        val appName = appContext.getString(com.victorl000.spotipass.R.string.app_name)
        println(appName)
    }
}