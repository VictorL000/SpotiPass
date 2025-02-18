package com.victorl000.spotipass.domain.repository

import com.victorl000.spotipass.model.SPProfile

interface ProfileRepository {
    fun getCurrentProfile(): SPProfile
    fun setCurrentProfile(p : SPProfile)
}