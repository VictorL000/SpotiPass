package com.victorl000.spotipass.apis

import com.victorl000.spotipass.model.AccountResponse
import com.victorl000.spotipass.model.SpotifyTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SpotifyApi {
    @GET("me")
    suspend fun getAccount(
        @Header("Authorization") apiKey: String,
    ) : AccountResponse

    @FormUrlEncoded
    @POST("api/token")
    suspend fun refreshToken(
        @Header("Authorization") authorization : String,
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
    ): SpotifyTokenResponse

    @FormUrlEncoded
    @POST("api/token")
    fun getAccessToken(
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): Call<SpotifyTokenResponse>
}