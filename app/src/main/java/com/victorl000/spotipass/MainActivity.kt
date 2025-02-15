package com.victorl000.spotipass

import android.app.ComponentCaller
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.victorl000.spotipass.ui.homeview.HomeView
import com.victorl000.spotipass.ui.loginview.LoginView
import com.victorl000.spotipass.ui.loginview.LoginViewModel
import com.victorl000.spotipass.ui.theme.SpotiPassTheme
import gupuru.streetpassble.StreetPassBle
import gupuru.streetpassble.parcelable.AdvertiseSuccess
import gupuru.streetpassble.parcelable.DeviceData
import gupuru.streetpassble.parcelable.StreetPassError
import gupuru.streetpassble.parcelable.StreetPassSettings
import gupuru.streetpassble.parcelable.TransferData
import kotlinx.serialization.Serializable

private const val LOGIN_REQUEST_CODE = 1337;
private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    var streetPassBle = StreetPassBle(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val loginViewModel: LoginViewModel by viewModels()
//
//        streetPassBle.setOnStreetPassBleListener(object : StreetPassBle.OnStreetPassBleListener {
//            override fun error(streetPassError: StreetPassError) {}
//
//            override fun receivedData(data: TransferData) {
//                loginViewModel.deviceList += (data.data)
//            }
//        })
//        streetPassBle.setOnStreetPassBleServerListener(object : StreetPassBle.OnStreetPassBleServerListener {
//            override fun onScanCallbackDeviceDataInfo(deviceData: DeviceData?) {}
//
//            override fun onScanCallbackError(streetPassError: StreetPassError?) {}
//
//            override fun onAdvertiseBleSuccess(advertiseSuccess: AdvertiseSuccess?) {}
//
//            override fun onAdvertiseBleError(streetPassError: StreetPassError?) {}
//
//            override fun onBLEServerRead(data: TransferData?) {
////                print(data?.data)
//                loginViewModel.deviceList += (data?.data ?: "")
//            }
//
//            override fun onBLEServerWrite(data: TransferData?) {}
//
//            override fun onBLEServerConnected(result: Boolean) {}
//
//            override fun onBLEServerError(streetPassError: StreetPassError?) {}
//
//            override fun onBLEGattServerServiceAdded(result: Boolean) {}
//
//            override fun onBLEGattServerCharacteristicWriteRequest(data: TransferData?) {}
//
//            override fun onBLEGattServerConnectionStateChange(
//                isConnect: Boolean,
//                device: BluetoothDevice?
//            ) {}
//        })
//
//        var streetPassSettings = StreetPassSettings.Builder()
//            .data("Hello from ${android.os.Build.MODEL}").build();
//        streetPassBle.start(streetPassSettings);

        enableEdgeToEdge()
        setContent {
            SpotiPassTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = LoginScreen
                ) {
                    composable<LoginScreen> {
                        LoginView(
                            onLogin = {
                                navController.navigate(HomeScreen)
                                BuildConfig.CLIENT_SECRET
                            }
                        )
                    }
                    composable<HomeScreen> {
                       HomeView()
                    }
                }
            }
        }
    }
//    override fun onStop() {
//        super.onStop();
//        streetPassBle.stop();
//    }
}


@Serializable
object LoginScreen

@Serializable
object HomeScreen
