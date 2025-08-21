package com.victorl000.spotipass.apis.repository

import com.victorl000.spotipass.domain.repository.UserProfileRepository
import com.victorl000.spotipass.model.SPProfile
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(

) : UserProfileRepository {
    override suspend fun getProfile(userId: String): SPProfile {
        delay(1000L)
        return SPProfile(
            profileId = "abc123",
            username = "funniguy743",
            spotifyUserId = "spotifyUserId",
            spotifyUrl = "localhost:3000",
            bio = "man i hope this all works out",
            integrations = emptyList()
        )
    }
}