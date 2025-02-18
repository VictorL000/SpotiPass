package com.victorl000.spotipass.apis

import com.victorl000.spotipass.model.AccountResponse
import com.victorl000.spotipass.model.SpotifyTokenResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AccountService {
    @GET("me")
    suspend fun getAccount(
        @Header("Authorization") apiKey: String,
    ) : AccountResponse

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

object AccountApi {

    val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = Level.BODY
    }

    val client : OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()

    val postClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
            chain.proceed(request)
        }.addInterceptor(interceptor)
        .build()

    private fun getAccountClient() : Retrofit {
        return Retrofit.Builder()
            .client(postClient)
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getApiClient() : Retrofit{
        return Retrofit.Builder()
            .client(postClient)
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService : AccountService = getApiClient().create(AccountService::class.java)
    val accountsService : AccountService = getAccountClient().create(AccountService::class.java)
}
