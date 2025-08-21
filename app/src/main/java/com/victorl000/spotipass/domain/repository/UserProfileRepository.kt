package com.victorl000.spotipass.domain.repository

import com.victorl000.spotipass.model.SPProfile

interface UserProfileRepository {
    suspend fun getProfile(userId: String) : SPProfile
}