package com.victorl000.di

import android.app.Application
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.apis.repository.BleRepositoryImpl
import com.victorl000.spotipass.domain.repository.BleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugModule {

    @Provides
    @Singleton
    fun provideBleApi() : BleApi{
        return object : BleApi {
            override fun bleStart() {
                println("STARTED DEBUG")
            }
        }
    }

    @Provides
    @Singleton
    fun provideBleRepository(api : BleApi, app : Application) : BleRepository {
        return BleRepositoryImpl(api, app)
    }
}
