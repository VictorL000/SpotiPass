package com.victorl000.spotipass.ui.homeview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.activity.viewModels
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import androidx.navigation.compose.rememberNavController
import com.victorl000.spotipass.ui.homeview.discover.HomeDiscoverView
import com.victorl000.spotipass.ui.homeview.discover.HomeDiscoverViewModel
import com.victorl000.spotipass.ui.homeview.maps.HomeMapsView
import com.victorl000.spotipass.ui.homeview.maps.HomeMapsViewModel
import com.victorl000.spotipass.ui.homeview.profile.HomeProfileView
import com.victorl000.spotipass.ui.homeview.profile.HomeProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePageView(
    homeViewModel: HomeViewModel,
    homeDiscoverViewModel: HomeDiscoverViewModel,
    homeMapsViewModel: HomeMapsViewModel,
    homeProfileViewModel: HomeProfileViewModel
) {

//    val state = homeViewModel.observeFlow().collectAsState()
    var selectedView = remember { mutableIntStateOf(1) }
    val items = listOf("Maps", "Discover", "Profile")
    val selectedIcons = listOf(Icons.Filled.LocationOn, Icons.Filled.Home, Icons.Filled.Person)
    val unselectedIcons =
        listOf(Icons.Outlined.LocationOn, Icons.Outlined.Home, Icons.Outlined.Person)

    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            NavigationBar() {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (selectedView.intValue == index) selectedIcons[index] else unselectedIcons[index],
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = selectedView.intValue == index,
                        onClick = {
                            selectedView.intValue = index
                            when(item) {
                                "Maps" -> {navController.navigate(MapsScreen)}
                                "Discover" -> {navController.navigate(DiscoverScreen)}
                                "Profile" -> {navController.navigate(ProfileScreen)}
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = DiscoverScreen, modifier = Modifier.padding(innerPadding)) {
            composable<DiscoverScreen> {
                HomeDiscoverView(homeDiscoverViewModel)
            }
            composable<MapsScreen> {
//                HomeMapsView(homeMapsViewModel)
            }
            composable<ProfileScreen> {
//                HomeProfileView(homeProfileViewModel)
            }
        }
    }
}

@Serializable
object MapsScreen

@Serializable
object ProfileScreen

@Serializable
object DiscoverScreen
