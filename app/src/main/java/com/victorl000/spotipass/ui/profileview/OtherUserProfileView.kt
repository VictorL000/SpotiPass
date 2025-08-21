package com.victorl000.spotipass.ui.profileview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.victorl000.spotipass.model.SPProfile

@Composable
fun OtherUserProfileView(vm: OtherUserProfileViewModel, userId: String) {
    val context = LocalContext.current
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = vm) {
        vm.loadUserProfile(userId)
    }
    when (val state = uiState) {
        is OtherUserProfileUiState.Loading -> {
            LoadingIndicator()
        }

        is OtherUserProfileUiState.Error -> {
            ErrorView(message = state.message)
        }

        is OtherUserProfileUiState.Success -> {
            UserProfileContent(profile = state.profile)
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun UserProfileContent(profile: SPProfile) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Username: ${profile.username}")
    }
}

@Composable
fun ErrorView(message: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error: ${message ?: "Unknown error"}", color = MaterialTheme.colorScheme.error)
    }
}