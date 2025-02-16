package com.victorl000.di

import android.app.Application
import android.bluetooth.BluetoothDevice
import android.content.Context
import com.victorl000.spotipass.apis.BleApi
import com.victorl000.spotipass.apis.repository.BleRepositoryImpl
import com.victorl000.spotipass.domain.repository.BleRepository
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
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReleaseModule {

    @Provides
    @Singleton
    fun provideBleApi() : BleApi{
        return object : BleApi {
            override fun bleStart(appContext : Application, flow : MutableStateFlow<String>) {
                var streetPassBle = StreetPassBle(appContext)

                streetPassBle.setOnStreetPassBleListener(object : StreetPassBle.OnStreetPassBleListener {
                    override fun error(streetPassError: StreetPassError) {}

                    override fun receivedData(data: TransferData) {
                        flow.value = (data.data)
                    }
                })
                streetPassBle.setOnStreetPassBleServerListener(object : StreetPassBle.OnStreetPassBleServerListener {
                    override fun onScanCallbackDeviceDataInfo(deviceData: DeviceData?) {}

                    override fun onScanCallbackError(streetPassError: StreetPassError?) {}

                    override fun onAdvertiseBleSuccess(advertiseSuccess: AdvertiseSuccess?) {}

                    override fun onAdvertiseBleError(streetPassError: StreetPassError?) {}

                    override fun onBLEServerRead(data: TransferData?) {
                        data?.let{ dataNonNull ->
                            flow.value = (dataNonNull.data)
                        }
                    }

                    override fun onBLEServerWrite(data: TransferData?) {}

                    override fun onBLEServerConnected(result: Boolean) {}

                    override fun onBLEServerError(streetPassError: StreetPassError?) {}

                    override fun onBLEGattServerServiceAdded(result: Boolean) {}

                    override fun onBLEGattServerCharacteristicWriteRequest(data: TransferData?) {}

                    override fun onBLEGattServerConnectionStateChange(
                        isConnect: Boolean,
                        device: BluetoothDevice?
                    ) {}
                })

                var streetPassSettings = StreetPassSettings.Builder()
                    .data("Hello from ${android.os.Build.MODEL}").build();
                streetPassBle.start(streetPassSettings);

            }
        }
    }

//    @Provides
//    @Singleton
//    fun provideBleRepository(api : BleApi, app : Application) : BleRepository {
//        return BleRepositoryImpl(api, app)
//    }
}
