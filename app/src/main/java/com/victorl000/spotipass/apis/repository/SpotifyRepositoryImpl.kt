package com.victorl000.spotipass.apis.repository

import android.util.Log
import com.victorl000.spotipass.BuildConfig.CLIENT_ID
import com.victorl000.spotipass.BuildConfig.CLIENT_SECRET
import com.victorl000.spotipass.apis.SpotifyApi
import com.victorl000.spotipass.domain.repository.CryptoRepository
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
    // not the greatest to have a repo in repo but i feel like it's alright in this one case
    private val cryptoRepository: CryptoRepository,
) : SpotifyRepository {
    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun refreshToken() : SpotifyTokenResponse {
        val refreshToken = cryptoRepository.getRefreshToken()
        if(refreshToken == null)
            throw Error("Refresh token in sharedpref has null value")

        return api.refreshToken(
            authorization = "Basic ${Base64.encode("$CLIENT_ID:$CLIENT_SECRET".toByteArray())}",
            refreshToken = refreshToken,
            clientId = CLIENT_ID,
        ).also {
            Log.d(TAG, "REFRESH TOKEN: " + it.refresh_token.toString())
            cryptoRepository.saveTokens(it.access_token, it.refresh_token)
        }
    }
}