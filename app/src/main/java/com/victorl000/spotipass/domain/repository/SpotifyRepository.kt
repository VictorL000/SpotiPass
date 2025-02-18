package com.victorl000.spotipass.domain.repository

import com.victorl000.spotipass.model.SpotifyTokenResponse

interface SpotifyRepository {
    suspend fun refreshToken() : SpotifyTokenResponse
    fun updateTokens(newAccessToken : String, newRefreshToken : String? = null)
}