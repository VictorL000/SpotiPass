package com.victorl000.spotipass.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.victorl000.spotipass.apis.CryptoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideCryptoApi() : CryptoApi {
        return object : CryptoApi {
            // Saving the token
            override fun saveAccessToken(context: Context, accessToken: String) {
                val prefs = getSecurePrefs(context)
                if(prefs == null) return
                prefs.edit()
                    .putString("access_token", accessToken)
                    .apply()
            }

            override fun saveRefreshToken(context: Context, refreshToken: String) {
                val prefs = getSecurePrefs(context)
                if(prefs == null) return
                prefs.edit()
                    .putString("refresh_token", refreshToken)
                    .apply()
            }

            // Retrieving the access token
            override fun getAccessToken(context: Context): String? {
                val prefs = getSecurePrefs(context)
                if(prefs == null) return null //TODO: check if should be null or ""
                return prefs.getString("access_token", null)
            }

            // Retrieving the refresh token
            override fun getRefreshToken(context: Context): String? {
                val prefs = getSecurePrefs(context)
                if(prefs == null) return null //TODO: check if should be null or ""
                return prefs.getString("refresh_token", null)
            }

            override fun isUserLoggedIn(context: Context): Boolean {
                val prefs = getSecurePrefs(context)
                if(prefs == null) return false
                return prefs.getBoolean("is_logged_in", false)
            }

            override fun saveLoginState(context: Context, isLoggedIn: Boolean) {
                val prefs = getSecurePrefs(context)
                if(prefs == null) return
                prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
            }

            private fun getSecurePrefs(context: Context): EncryptedSharedPreferences? {
                val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                try {
                    return EncryptedSharedPreferences.create(
                        "secure_prefs",
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    ) as EncryptedSharedPreferences
                } catch (e: Exception) {
                    return null
                }
            }
        }
    }
}
