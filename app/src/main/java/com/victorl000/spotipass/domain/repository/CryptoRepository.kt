package com.victorl000.spotipass.domain.repository

interface CryptoRepository {
    fun saveTokens(accessToken : String, refreshToken : String? = null)
    fun getAccessToken(): String?
    fun saveLoginState(loggedIn : Boolean)
    fun isUserLoggedIn(): Boolean
}