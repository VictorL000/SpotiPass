package com.victorl000.spotipass.apis.repository

import com.victorl000.spotipass.BuildConfig.CLIENT_ID
import com.victorl000.spotipass.BuildConfig.CLIENT_SECRET
import com.victorl000.spotipass.apis.SpotifyApi
import com.victorl000.spotipass.domain.repository.SpotifyRepository
import com.victorl000.spotipass.model.SpotifyTokenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Named
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

private const val TAG = "SpotifyRepositoryImpl"

class SpotifyRepositoryImpl @Inject constructor(
    private val api: SpotifyApi,
    @Named("accessToken") private val accessTokenFlow : MutableStateFlow<String?>,
    @Named("refreshToken") private val refreshTokenFlow : MutableStateFlow<String?>,
    @Named("loggedIn") private val loggedInFlow : MutableStateFlow<Boolean>,
) : SpotifyRepository {
    override fun updateTokens(newAccessToken : String, newRefreshToken : String?) {
        accessTokenFlow.value = newAccessToken
        if(newRefreshToken != null) refreshTokenFlow.value = newRefreshToken
    }
    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun refreshToken() : SpotifyTokenResponse {
        refreshTokenFlow.value?.let { refreshToken ->
            return api.refreshToken(
                authorization = "Basic ${Base64.encode("$CLIENT_ID:$CLIENT_SECRET".toByteArray())}",
                refreshToken = refreshToken,
                clientId = CLIENT_ID,
            ).also {
                accessTokenFlow.value = it.access_token
                if(it.refresh_token != null) refreshTokenFlow.value = it.refresh_token
            }
        }
        throw Error("Refresh token flow has null value")
    }
}