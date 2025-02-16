package com.victorl000.di

import android.app.Application
import com.victorl000.spotipass.apis.BleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object DebugModule {

    @Provides
    @Singleton
    fun provideBleApi() : BleApi{
        return object : BleApi {
            override fun bleStart(appContext : Application, flow : MutableStateFlow<String>) {
                flow.value = ("WHAT! ${Random.nextInt()}")
            }
        }
    }
//
//    @Provides
//    @Singleton
//    fun provideBleRepository(api : BleApi, app : Application) : BleRepository {
//        return BleRepositoryImpl(api, app)
//    }
}
