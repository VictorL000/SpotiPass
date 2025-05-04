package com.victorl000.di

import android.app.Application
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.apis.repository.BleRepositoryImpl
import com.victorl000.spotipass.domain.repository.BleRepository
import com.victorl000.spotipass.model.SPReceivedData
import com.victorl000.spotipass.model.SPTransmittedData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gupuru.streetpassble.StreetPassBle
import gupuru.streetpassble.parcelable.AdvertiseSuccess
import gupuru.streetpassble.parcelable.DeviceData
import gupuru.streetpassble.parcelable.StreetPassError
import gupuru.streetpassble.parcelable.StreetPassSettings
import gupuru.streetpassble.parcelable.TransferData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReleaseModule {

    @Provides
    @Singleton
    fun provideBleApi(
        scope: CoroutineScope
    ) : BleApi{
        var streetPassBle: StreetPassBle
        val json = Json{prettyPrint = true}
        return object : BleApi {
            override fun bleStart(appContext : Application) : StateFlow<SPReceivedData?> {
                val flow = MutableStateFlow<SPReceivedData?>(null)
                streetPassBle = StreetPassBle(appContext)

                streetPassBle.setOnStreetPassBleListener(object : StreetPassBle.OnStreetPassBleListener {
                    override fun error(streetPassError: StreetPassError) {}

                    override fun receivedData(data: TransferData) {
                        Log.d("BLE-RECEIVEDDATA", data.data)
                        val spdata = json.decodeFromString<SPReceivedData>(data.data)
                        scope.launch{
                            flow.emit(spdata)
                        }
                    }
                })
                streetPassBle.setOnStreetPassBleServerListener(object : StreetPassBle.OnStreetPassBleServerListener {
                    override fun onScanCallbackDeviceDataInfo(deviceData: DeviceData?) {}

                    override fun onScanCallbackError(streetPassError: StreetPassError?) {}

                    override fun onAdvertiseBleSuccess(advertiseSuccess: AdvertiseSuccess?) {}

                    override fun onAdvertiseBleError(streetPassError: StreetPassError?) {}

                    override fun onBLEServerRead(data: TransferData?) {
                        data?.let { data ->
                            Log.d("BLE-SERVERREAD", data.data)
                            try {
                                val spdata = json.decodeFromString<SPReceivedData>(data.data)
                                scope.launch{
                                    flow.emit(spdata)
                                }
                            } catch (e: Error) {
                                // probably serialization issue
                            }
                        }
                    }

                    override fun onBLEServerWrite(data: TransferData?) {
                        data?.let { data ->
                            Log.d("BLE-SERVERWRITE", data.data)
                        }
                    }

                    override fun onBLEServerConnected(result: Boolean) {}

                    override fun onBLEServerError(streetPassError: StreetPassError?) {}

                    override fun onBLEGattServerServiceAdded(result: Boolean) {}

                    override fun onBLEGattServerCharacteristicWriteRequest(data: TransferData?) {
                        data?.let { data ->
                            Log.d("BLE-SERVERCHARACTERISTICWRITE", data.data)
                        }
                    }

                    override fun onBLEGattServerConnectionStateChange(
                        isConnect: Boolean,
                        device: BluetoothDevice?
                    ) {}
                })

                val jsonString = json.encodeToString(getMockAccount())
                Log.d("BLE-PROFILE", jsonString)
                var streetPassSettings = StreetPassSettings.Builder()
                    .data(jsonString).build();
                try {
                    streetPassBle.start(streetPassSettings);
                } catch (e: Error) {
                    Log.wtf("BLE", e)
                }
                Log.d("BLE", "STARTED")
                return flow.asStateFlow()
            }


            override fun updateBroadcastMessage(newBroadcast: SPTransmittedData) {
//                TODO("Not yet implemented")
            }
        }
    }

    private fun getMockAccount() = SPReceivedData(
        profileId = UUID.randomUUID().toString(),
        username = "${android.os.Build.MODEL}",
        spotifyUserId = "22zc36dej2wpy6dm23eu5bsqq",
        spotifyUrl = "https://open.spotify.com/user/22zc36dej2wpy6dm23eu5bsqq?si=0ffbb5a380854a0c",
        timestamp = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

//    @Provides
//    @Singleton
//    fun provideBleRepository(api : BleApi, app : Application) : BleRepository {
//        return BleRepositoryImpl(api, app)
//    }
}
