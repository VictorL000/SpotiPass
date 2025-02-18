package com.victorl000.spotipass.di

import com.victorl000.spotipass.apis.AccountApi.interceptor
import com.victorl000.spotipass.apis.SpotifyApi
import com.victorl000.spotipass.model.SpotifyTokenResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotifyModule {

    private val postClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
            chain.proceed(request)
        }.addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun getAccountClient() : SpotifyApi {
        return Retrofit.Builder()
            .client(postClient)
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)
    }
}