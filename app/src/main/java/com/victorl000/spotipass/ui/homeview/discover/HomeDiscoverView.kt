package com.victorl000.spotipass.ui.homeview.discover

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.victorl000.spotipass.model.SPReceivedData
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

@Composable
fun HomeDiscoverView(
    homeDiscoverViewModel: HomeDiscoverViewModel
) {
    val discoverFlow = homeDiscoverViewModel.observeDiscoverListFlow().collectAsState()
    DiscoveredProfileList(discoverFlow.value)
}

@Preview
@Composable
fun DiscoveredProfileList(
    @PreviewParameter(DiscoverPreviewParameterProvider::class) items : List<SPReceivedData>
) {
    LazyColumn (){
        items.forEach {
            item {
                DiscoveredProfileRow(it)
            }
        }
    }
}

@Composable
fun DiscoveredProfileRow(
    profileData : SPReceivedData
) {
    val context = LocalContext.current
    Row (modifier = Modifier.padding(15.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Box(modifier = Modifier.background(Color.Blue, CircleShape).size(45.dp))
        Column (modifier = Modifier.padding(15.dp).weight(1f)){
            Text(fontSize = 20.sp, text = profileData.username)
            Text(timeAgo(profileData.timestamp))
        }
        Button(
            contentPadding = PaddingValues(
                start = 10.dp,
                top = 6.dp,
                end = 10.dp,
                bottom = 6.dp
            ),
            onClick = {
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

class DiscoverPreviewParameterProvider : PreviewParameterProvider<List<SPReceivedData>> {
    override val values = sequenceOf(
        listOf(getMockAccount(), getMockAccount(), getMockAccount()),
    )
}

private fun getMockAccount () = SPReceivedData(
    profileId = UUID.randomUUID().toString(),
    username = "funniguy743",
    spotifyUserId = "22zc36dej2wpy6dm23eu5bsqq",
    spotifyUrl = "https://open.spotify.com/user/22zc36dej2wpy6dm23eu5bsqq?si=0ffbb5a380854a0c",
    timestamp = LocalDateTime.now()
        .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
)

private fun timeAgo(timestamp: Long): String {
    val localDateTime = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val now = LocalDateTime.now()
    val duration = Duration.between(localDateTime, now)

    return when {
        duration.seconds < 60 -> "Just now"
        duration.toMinutes() < 60 ->
            "${duration.toMinutes()} minute${if (duration.toMinutes() == 1L) "" else "s"} ago"
        duration.toHours() < 24 ->
            "${duration.toHours()} hour${if (duration.toHours() == 1L) "" else "s"} ago"
        duration.toDays() < 7 ->
            "${duration.toDays()} day${if (duration.toDays() == 1L) "" else "s"} ago"
        duration.toDays() < 30 ->
            "${duration.toDays() / 7} week${if (duration.toDays() / 7 == 1L) "" else "s"} ago"
        duration.toDays() < 365 ->
            "${duration.toDays() / 30} month${if (duration.toDays() / 30 == 1L) "" else "s"} ago"
        else ->
            "${duration.toDays() / 365} year${if (duration.toDays() / 365 == 1L) "" else "s"} ago"
    }
}