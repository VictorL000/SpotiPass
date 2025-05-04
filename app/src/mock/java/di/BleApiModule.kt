package di

import android.app.Application
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object BleApiModule {

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideBleApi() : BleApi {
        return object : BleApi {
            private val TAG = "BleApi"
            var message = getMockAccount()
            override fun bleStart(appContext : Application) : StateFlow<SPReceivedData?> {
                val flow = MutableStateFlow<SPReceivedData?>(null)
                GlobalScope.launch { // JUST FOR DEBUG PURPOSES
                    repeat(20) {
                        delay(2000)
                        flow.emit(message)
                    }
                }
                return flow.asStateFlow()
            }
            override fun updateBroadcastMessage(newMessage : SPTransmittedData) {
                message = SPReceivedData(
                    newMessage.profileId,
                    newMessage.username,
                    newMessage.spotifyUserId,
                    newMessage.spotifyUrl,
                    timestamp = LocalDateTime.now().minusSeconds(Random.nextLong(3600)).atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli())
            }
        }
    }

    private fun getMockAccount (minusSeconds : Long = 0) = SPReceivedData(
        profileId = UUID.randomUUID().toString(),
        username = "funniguy743",
        spotifyUserId = "22zc36dej2wpy6dm23eu5bsqq",
        spotifyUrl = "https://open.spotify.com/user/22zc36dej2wpy6dm23eu5bsqq?si=0ffbb5a380854a0c",
        timestamp = LocalDateTime.now().minusSeconds(minusSeconds).atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
}
