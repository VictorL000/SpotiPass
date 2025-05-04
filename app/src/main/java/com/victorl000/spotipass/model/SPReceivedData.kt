package com.victorl000.spotipass.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class SPReceivedData(
    val profileId : String,
    val username : String,
    val spotifyUserId : String,
    val spotifyUrl : String,
    val timestamp: Long,
//    val location: Location
)
