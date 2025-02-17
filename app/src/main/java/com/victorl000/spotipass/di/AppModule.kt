package com.victorl000.spotipass.di

import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.model.SPReceivedData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
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
}