package com.victorl000.spotipass.ui.homeview.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.victorl000.spotipass.model.SPProfile
import com.victorl000.spotipass.ui.homeview.ProfileScreen
import com.victorl000.spotipass.ui.homeview.discover.DiscoveredProfileList
import java.util.UUID


@Composable
fun HomeProfileView(
    homeProfileViewModel: HomeProfileViewModel
) {
    val profileFlow = homeProfileViewModel.observeProfileFlow().collectAsState()
    Column (modifier = Modifier.padding(15.dp).fillMaxWidth()){
        Text("IDK")
        TextField(value = profileFlow.value?.username ?: "", onValueChange = {homeProfileViewModel.updateValue(profileFlow.value?.copy(username = it) ?: SPProfile(
            profileId = UUID.randomUUID().toString(),
            username = it,
            spotifyUserId = "",
            spotifyUrl = "",
        ))})
    }
}