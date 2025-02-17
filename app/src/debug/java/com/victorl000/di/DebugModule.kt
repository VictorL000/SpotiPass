package com.victorl000.di

import android.app.Application
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.model.SPTransferData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.TimeZone
import java.util.UUID
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object DebugModule {

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideBleApi() : BleApi{
        return object : BleApi {
            override fun bleStart(appContext : Application, flow : MutableStateFlow<SPTransferData?>) {
                GlobalScope.launch { // JUST FOR DEBUG PURPOSES
                    repeat(20) {
                        delay(2000)
                        flow.value = getMockAccount()
                    }
                }
            }
        }
    }
    private fun getMockAccount () = SPTransferData(
        profileId = UUID.randomUUID().toString(),
        username = "funniguy743",
        spotifyUserId = "22zc36dej2wpy6dm23eu5bsqq",
        spotifyUrl = "https://open.spotify.com/user/22zc36dej2wpy6dm23eu5bsqq?si=0ffbb5a380854a0c",
        timestamp = LocalDateTime.now()
    )
//
//    @Provides
//    @Singleton
//    fun provideBleRepository(api : BleApi, app : Application) : BleRepository {
//        return BleRepositoryImpl(api, app)
//    }
}
