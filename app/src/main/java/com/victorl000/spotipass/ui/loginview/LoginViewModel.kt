package com.victorl000.spotipass.ui.loginview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Base64
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.CODE
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.ERROR
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.TOKEN
import com.victorl000.spotipass.model.AccountResponse
import com.victorl000.spotipass.apis.SpotifyApi
import com.victorl000.spotipass.model.SpotifyTokenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.arrayOf

private const val TAG = "LoginViewModel"
private const val LOGIN_REQUEST_CODE = 1337;
private const val REDIRECT_URI = "http://localhost:3000";
private const val CLIENT_ID = "b7466ef8100441a292c63908d0104488"


class LoginViewModel : ViewModel() {
    private val _accountState = MutableStateFlow<AccountState>(AccountState.Loading)
    val accountState = _accountState.asStateFlow()
    var accessToken : String? = null

    fun login(context: Context, authLauncher:  ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val codeVerifier = generateCodeVerifier()
        val codeChallenge = generateCodeChallenge(codeVerifier)

        saveCodeVerifier(context, codeVerifier)

        val request = AuthorizationRequest.Builder(
            CLIENT_ID,
            CODE,
            REDIRECT_URI
        )
            .setScopes(arrayOf("user-read-email", "user-read-private"))
            .setCustomParam("code_challenge", "eEC-psWDuBsyFsm7r0vMtKrjJ1E66-GLgyCNNRH9PU0")
            .setCustomParam("code_challenge_method", "S256")
            .build()
        Log.d(TAG, request.toUri().toString())
        val intent = AuthorizationClient.createLoginActivityIntent(context as Activity, request)
        authLauncher.launch(intent)
    }

    fun getSpotifyLoginResponse( context: Context, result: ActivityResult ) {
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
            CODE -> {
                Log.d(TAG, "Authentication success: ${response.code}")
                viewModelScope.launch {
                    exchangeCodeForToken(context = context, response.code)
//                    try {
//                        val call = SpotifyApi.accountsService.getAccessToken(
//                            code = response.code,
//                            redirectUri = "http://localhost:3000",
//                            clientId = CLIENT_ID,
//                            clientSecret = "1857bfd667d34a12a2b02f94725bd640"
//                        )
//
//                        call.enqueue(object : Callback<SpotifyTokenResponse> {
//                            override fun onResponse(
//                                call: Call<SpotifyTokenResponse>,
//                                response: Response<SpotifyTokenResponse>
//                            ) {
//                                if (response.isSuccessful) {
//                                    val tokenResponse = response.body()
//                                    Log.d(TAG, "Access Token: ${tokenResponse?.access_token}")
//                                    Log.d(TAG, "Refresh Token: ${tokenResponse?.refresh_token}")
//                                } else {
//                                    println("Error: ${response.errorBody()?.string()}")
//                                }
//                            }
//
//                            override fun onFailure(call: Call<SpotifyTokenResponse>, t: Throwable) {
//                                println("Request failed: ${t.message}")
//                            }
//                        })
//                    } catch (e: Exception){
//                        _accountState.value = AccountState.Error("Failed to fetch user $e")
//                        Log.d(TAG, e.toString())
//                    }
                }
            }
            else -> {
                Log.d(TAG, "Unexpected response: ${response.type}")
            }
        }
    }
    private val showLoginActivityToken = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        val authorizationResponse = AuthorizationClient.getResponse(result.resultCode, result.data)

        when (authorizationResponse.type) {
            TOKEN -> {
                Log.d(TAG, authorizationResponse.accessToken)
                // Here You can get access to the authorization token
                // with authorizationResponse.token
            }
            ERROR -> print(1)
            // Handle Error
            else -> print(2)
            // Probably interruption
        }
    }
    fun getLoginActivityTokenIntent(context: Context, code: String): Intent =
        AuthorizationClient.createLoginActivityIntent(
            context as Activity,
            AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
                .setCustomParam("grant_type", "authorization_code")
                .setCustomParam("code", code)
                .setCustomParam("code_verifier", "CSZspLIJEuo1KakmnhXrherrii5nTjdTYGYrVM0KmHk")
                .build()
        )
    suspend fun exchangeCodeForToken(context: Context, authCode: String) {
        var activity : Activity = context as Activity
        var context : Context = activity as Context
        val codeVerifier = getCodeVerifier(context)
        if (codeVerifier.isNullOrEmpty()) {
            Log.e("PKCE", "Code verifier is missing!")
            return
        }

        val response = SpotifyApi.accountsService.getAccessToken(
            code = authCode,
            redirectUri = REDIRECT_URI,
            clientId = CLIENT_ID,
            codeVerifier = codeVerifier
        )

        if (response.isSuccessful) {
            val tokenResponse = response.body()
            Log.d("PKCE", "Access Token: ${tokenResponse?.access_token}")
        } else {
            Log.e("PKCE", "Error: ${response.errorBody()?.string()}")
        }
    }


    fun fetchSpotifyAccount() {

        accessToken?.let{ accessToken ->
            viewModelScope.launch {
                try {
                    val response = SpotifyApi.apiService.getAccount(
                        apiKey = "Bearer $accessToken"
                    )
                    _accountState.value = AccountState.Success(response)
                    Log.d(TAG, response.uri)
                } catch (e: Exception){
                    _accountState.value = AccountState.Error("Failed to fetch user $e")
                    Log.d(TAG, e.toString())
                }
            }
        }
    }
    private fun generateCodeVerifier(): String {
        val bytes = ByteArray(64)
        SecureRandom().nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
    }
    private fun generateCodeChallenge(codeVerifier: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(codeVerifier.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
    }
    private fun saveCodeVerifier(context: Context, codeVerifier: String) {
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        prefs.edit().putString("code_verifier", codeVerifier).apply()
    }

    private fun getCodeVerifier(context: Context): String? {
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return prefs.getString("code_verifier", null)
    }

}

sealed class AccountState {
    object Loading : AccountState()
    data class Success(val accountData : AccountResponse) : AccountState()
    data class Error(val message : String) : AccountState()
}