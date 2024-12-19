package com.victorl000.spotipass.model

data class AccountResponse(
    val country: String,
    val display_name: String,
    val email: String,
    val explicit_content: ExplicitContent,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val product: String,
    val type: String,
    val uri: String
)

data class ExplicitContent(
    val filter_enabled: Boolean,
    val filter_locked: Boolean
)

data class ExternalUrls(
    val spotify: String
)

data class Followers(
    val href: String,
    val total: Int
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int
)

data class SpotifyTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String?,
    val scope: String
)
