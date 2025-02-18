package com.victorl000.spotipass.apis.repository

import android.app.Application
import android.util.Log
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.apis.CryptoApi
import com.victorl000.spotipass.domain.repository.CryptoRepository
import com.victorl000.spotipass.model.SPReceivedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Named
import kotlin.math.log

class CryptoRepositoryImpl @Inject constructor(
    private val cryptoApi : CryptoApi,
    private val appContext : Application,
//    @Named("accessToken") private val accessTokenFlow : MutableStateFlow<String?>,
//    @Named("refreshToken") private val refreshTokenFlow : MutableStateFlow<String?>,
//    @Named("loggedIn") private val loggedInFlow : MutableStateFlow<Boolean>,
) : CryptoRepository {
    override fun saveTokens(accessToken : String, refreshToken : String?) {
        cryptoApi.saveRefreshToken(appContext, accessToken)
        if(refreshToken != null)
            cryptoApi.saveRefreshToken(appContext, refreshToken)
    }

    override fun getAccessToken(): String? {
        return cryptoApi.getAccessToken(appContext)
    }

    override fun saveLoginState(loginState : Boolean) {
        cryptoApi.saveLoginState(appContext, loginState)
    }

    override fun isUserLoggedIn(): Boolean {
        return cryptoApi.isUserLoggedIn(appContext)
    }
}