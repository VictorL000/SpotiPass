package com.victorl000.spotipass.model

import java.time.LocalDateTime

data class SPTransmittedData(
    val profileId : String,
    val username : String,
    val spotifyUserId : String,
    val spotifyUrl : String,
)
