package com.victorl000.spotipass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victorl000.spotipass.SplashScreen
import com.victorl000.spotipass.ui.homeview.HomePageView
import com.victorl000.spotipass.ui.homeview.HomeViewModel
import com.victorl000.spotipass.ui.homeview.discover.HomeDiscoverViewModel
import com.victorl000.spotipass.ui.homeview.maps.HomeMapsViewModel
import com.victorl000.spotipass.ui.homeview.profile.HomeProfileViewModel
import com.victorl000.spotipass.ui.loginview.LoginView
import com.victorl000.spotipass.ui.loginview.LoginViewModel
import com.victorl000.spotipass.ui.splashview.SplashView
import com.victorl000.spotipass.ui.splashview.SplashViewModel
import com.victorl000.spotipass.ui.theme.SpotiPassTheme
import dagger.hilt.android.AndroidEntryPoint
import gupuru.streetpassble.StreetPassBle
import kotlinx.serialization.Serializable

private const val LOGIN_REQUEST_CODE = 1337
private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var streetPassBle = StreetPassBle(this)
    private val homeViewModel : HomeViewModel by viewModels()
    private val homeDiscoverViewModel : HomeDiscoverViewModel by viewModels()
    private val homeMapsViewModel : HomeMapsViewModel by viewModels()
    private val homeProfileViewModel : HomeProfileViewModel by viewModels()
    private val loginViewModel : LoginViewModel by viewModels()
    private val splashViewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotiPassTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SplashScreen
                ) {
                    composable<SplashScreen> {
                        SplashView (
                            splashViewModel,
                            {navController.navigate(HomeScreen)},
                            {navController.navigate(LoginScreen)},
                        )
                    }
                    composable<LoginScreen> {
                        LoginView(
                            loginViewModel,
                            onLogin = {
                                navController.navigate(HomeScreen)
                                BuildConfig.CLIENT_SECRET
                            }
                        )
                    }
                    composable<HomeScreen> {
                        HomePageView(homeViewModel, homeDiscoverViewModel, homeMapsViewModel, homeProfileViewModel)
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

@Serializable
object SplashScreen
