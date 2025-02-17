package com.victorl000.spotipass.model

import android.location.Location
import java.time.LocalDateTime

data class SPTransferData(
    val profileId : String,
    val username : String,
    val spotifyUserId : String,
    val spotifyUrl : String,
    val timestamp: LocalDateTime,
//    val location: Location
)
