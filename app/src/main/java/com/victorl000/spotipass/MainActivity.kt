package com.victorl000.spotipass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.victorl000.spotipass.ui.theme.SpotiPassTheme
import gupuru.streetpassble.StreetPassBle
import gupuru.streetpassble.parcelable.StreetPassError
import gupuru.streetpassble.parcelable.TransferData

class MainActivity : ComponentActivity() {
    var streetPassBle = StreetPassBle(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        streetPassBle.setOnStreetPassBleListener(object : StreetPassBle.OnStreetPassBleListener {
            override fun error(streetPassError: StreetPassError) {
                print("error $streetPassError")
            }

            override fun receivedData(data: TransferData) {
                print("received $data")
            }
        })
        streetPassBle.start();
        enableEdgeToEdge()
        setContent {
            SpotiPassTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    override fun onStop() {
        super.onStop();
        streetPassBle.stop();
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpotiPassTheme {
        Greeting("Android")
    }
}