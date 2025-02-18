package com.victorl000.spotipass.apis.repository

import android.app.Application
import android.util.Log
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.apis.CryptoApi
import com.victorl000.spotipass.apis.ProfileApi
import com.victorl000.spotipass.domain.repository.CryptoRepository
import com.victorl000.spotipass.domain.repository.ProfileRepository
import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.model.SPReceivedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Named
import kotlin.math.log

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val appContext : Application,
) : ProfileRepository {
    override fun getCurrentProfile(): SPProfile {
        return profileApi.getCurrentProfile()
    }

    override fun setCurrentProfile(p : SPProfile) {
        profileApi.setCurrentProfile(p)
    }
}