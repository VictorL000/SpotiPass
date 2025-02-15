package com.victorl000.spotipass.ui.loginview

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.victorl000.spotipass.BuildConfig

private const val TAG = "LoginView"

@Composable
fun LoginView(onLogin: () -> Unit) {
    val vm = viewModel<LoginViewModel>()
    val context = LocalContext.current

    val authLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result -> vm.getSpotifyLoginResponse(result, onLogin) }

    Column (modifier = Modifier.padding(top = 200.dp).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Button (onClick = {vm.login(context, authLauncher)}) {
            Text("Login")
        }
        Button (onClick = {vm.fetchSpotifyAccount()}) {
            Text("Fetch")
        }
    }
}