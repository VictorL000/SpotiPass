package com.victorl000.spotipass.ui.homeview.discover

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.victorl000.spotipass.model.SPTransferData
import javax.inject.Inject

@Composable
fun HomeDiscoverView(
    homeDiscoverViewModel: HomeDiscoverViewModel
) {
    val discoverFlow = homeDiscoverViewModel.observeDiscoverListFlow().collectAsState()
    LazyColumn {
        discoverFlow.value.forEach {
            item {
                DiscoveredProfileRow(it)
            }
        }
    }
}

@Composable
fun DiscoveredProfileRow(
    profileData : SPTransferData
) {
    val context = LocalContext.current
    Row {
        Column {
            Text(profileData.username)
            Text(profileData.timestamp.toString())
        }
        Button(onClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(profileData.spotifyUrl))
            context.startActivity(browserIntent)
        }) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Open in Spotify"
            )
        }
    }
}