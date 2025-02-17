package com.victorl000.spotipass.model

import java.time.LocalDateTime

data class SPReceivedData(
    val profileId : String,
    val username : String,
    val spotifyUserId : String,
    val spotifyUrl : String,
    val timestamp: LocalDateTime,
//    val location: Location
)
