package com.victorl000.di

import com.victorl000.spotipass.apis.AccountApi.interceptor
import com.victorl000.spotipass.apis.ProfileApi
import com.victorl000.spotipass.apis.SpotifyApi
import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.model.SPReceivedData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Singleton
    fun getProfileApiClient() : ProfileApi {
        return object : ProfileApi {
            var mockProfile = SPProfile(
                profileId = UUID.randomUUID().toString(),
                username = "funniguy743",
                spotifyUserId = "22zc36dej2wpy6dm23eu5bsqq",
                spotifyUrl = "https://open.spotify.com/user/22zc36dej2wpy6dm23eu5bsqq?si=0ffbb5a380854a0c",
            )
            override fun getCurrentProfile(): SPProfile {
                return mockProfile
            }
            override fun setCurrentProfile(profile: SPProfile) {
                mockProfile = profile
            }
        }
    }

}