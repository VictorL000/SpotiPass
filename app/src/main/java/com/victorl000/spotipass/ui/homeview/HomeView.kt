package com.victorl000.spotipass.ui.homeview

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@Composable
fun HomeView(
    homeViewModel: HomeViewModel
) {

    val state = homeViewModel.observeFlow().collectAsState()
    var selectedView = remember { mutableIntStateOf(0) }
    val items = listOf("Maps", "Home", "Profile")
    val selectedIcons = listOf(Icons.Filled.LocationOn, Icons.Filled.Home, Icons.Filled.Person)
    val unselectedIcons =
        listOf(Icons.Outlined.LocationOn, Icons.Outlined.Home, Icons.Outlined.Person)

    Column() {
        Column {
            Text(text = state.value)
            Button(onClick = { homeViewModel.updateValue("New Value") }) {
                Text("Update")
            }
        }
        Button(onClick = {homeViewModel.say()}) {Text("what")}
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
                    onClick = { selectedView.intValue = index }
                )
            }
        }
    }
}
