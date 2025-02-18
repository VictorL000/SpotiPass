package com.victorl000.spotipass.di

import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.model.SPReceivedData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideBleApi() : BleApi{
//        return object : BleApi {
//            override fun bleStart() {
//                println("STARTED")
//            }
//        }
//    }
//

    @Provides
    @Singleton
    fun provideTransferData() : MutableStateFlow<SPReceivedData?> = MutableStateFlow(null)

    @Provides
    @Singleton
    fun provideTransferListFlow() : MutableStateFlow<List<SPReceivedData>> = MutableStateFlow(emptyList())

    @Provides
    @Singleton
    fun provideProfileFlow() : MutableStateFlow<SPProfile?> = MutableStateFlow(null)

    @Provides
    @Singleton
    @Named("accessToken")
    fun provideAccessTokenFlow() : MutableStateFlow<String?> = MutableStateFlow(null)

    @Provides
    @Singleton
    @Named("refreshToken")
    fun provideRefreshTokenFlow() : MutableStateFlow<String?> = MutableStateFlow(null)

    @Provides
    @Singleton
    @Named("loggedIn")
    fun provideLoggedInFlow() : MutableStateFlow<Boolean> = MutableStateFlow(false)
}