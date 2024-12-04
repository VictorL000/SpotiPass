package com.victorl000.spotipass.ui.loginview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.ERROR
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.arrayOf

private const val TAG = "LoginViewModel"
private const val LOGIN_REQUEST_CODE = 1337;
private const val REDIRECT_URI = "http://localhost:3000";
private const val CLIENT_ID = "b7466ef8100441a292c63908d0104488"

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()
    var accessToken : String? = null

    fun login(context: Context, authLauncher:  ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val request = AuthorizationRequest.Builder(
            CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        )
            .setScopes(arrayOf("user-read-email", "user-read-private"))
            .build()

        val intent = AuthorizationClient.createLoginActivityIntent(context as Activity, request)
        authLauncher.launch(intent)
    }

    fun getSpotifyLoginResponse( result: ActivityResult ) {
        val response = AuthorizationClient.getResponse(result.resultCode, result.data)
        when (response.type) {
            TOKEN -> {
                accessToken = response.accessToken
                Log.d(TAG, "Authentication success: $accessToken")

//                onAuthSuccess(accessToken)
            }
            ERROR -> {
                Log.d(TAG, "Authentication failed: ${response.error}")
            }
            else -> {
                Log.d(TAG, "Unexpected response: ${response.type}")
            }
        }
    }
    suspend fun getSpotifyAccount() {
        if(accessToken != null) {

        }
    }
}
