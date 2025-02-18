package com.victorl000.spotipass.ui.loginview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.CODE
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.ERROR
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.TOKEN
import com.victorl000.spotipass.BuildConfig
import com.victorl000.spotipass.model.AccountResponse
import com.victorl000.spotipass.apis.AccountApi
import com.victorl000.spotipass.domain.repository.SpotifyRepository
import com.victorl000.spotipass.model.SpotifyTokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.arrayOf

private const val TAG = "LoginViewModel"
private const val LOGIN_REQUEST_CODE = 1337;
private const val REDIRECT_URI = BuildConfig.REDIRECT_URI
private const val CLIENT_ID = BuildConfig.CLIENT_ID


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val spotifyRepository: SpotifyRepository
): ViewModel() {
    private val _accountState = MutableStateFlow<AccountState>(AccountState.Loading)
    val accountState = _accountState.asStateFlow()
    var accessToken : String? = null

    fun login(context: Context, authLauncher:  ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val request = AuthorizationRequest.Builder(
            CLIENT_ID,
            CODE,
            REDIRECT_URI
        )
            .setScopes(arrayOf("user-read-email", "user-read-private"))
            .build()

        val intent = AuthorizationClient.createLoginActivityIntent(context as Activity, request)
        authLauncher.launch(intent)
    }

    fun getSpotifyLoginResponse( result: ActivityResult, onLogin : () -> Unit ) {
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
                Log.d(TAG, "Authentication success: ${response.accessToken}")
//                exchangeCodeForToken(context = )
                viewModelScope.launch {
                    try {
                        val call = AccountApi.accountsService.getAccessToken(
                            code = response.code,
                            redirectUri = BuildConfig.REDIRECT_URI,
                            clientId = CLIENT_ID,
                            clientSecret = BuildConfig.CLIENT_SECRET
                        )

                        call.enqueue(object : Callback<SpotifyTokenResponse> {
                            override fun onResponse(
                                call: Call<SpotifyTokenResponse>,
                                response: Response<SpotifyTokenResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val tokenResponse = response.body()
                                    Log.d(TAG, "Access Token: ${tokenResponse?.access_token}")
                                    Log.d(TAG, "Refresh Token: ${tokenResponse?.refresh_token}")
                                    if(tokenResponse != null)
                                        spotifyRepository.updateTokens(tokenResponse.access_token, tokenResponse?.refresh_token)
                                    onLogin()
                                } else {
                                    println("Error: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<SpotifyTokenResponse>, t: Throwable) {
                                println("Request failed: ${t.message}")
                            }
                        })
                    } catch (e: Exception){
                        _accountState.value = AccountState.Error("Failed to fetch user $e")
                        Log.d(TAG, e.toString())
                    }
                }
            }
            else -> {
                Log.d(TAG, "Unexpected response: ${response.type}")
            }
        }
    }


    fun fetchSpotifyAccount() {
        accessToken?.let{ accessToken ->
            viewModelScope.launch {
                try {
                    val response = AccountApi.apiService.getAccount(
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

}

sealed class AccountState {
    object Loading : AccountState()
    data class Success(val accountData : AccountResponse) : AccountState()
    data class Error(val message : String) : AccountState()
}