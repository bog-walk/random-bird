package dev.bogwalk.models

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashData(val results: List<UnsplashPhoto>)

@Serializable
data class UnsplashPhoto(
    val id: String,
    val likes: Int,
    val description: String?,
    val user: UnsplashUser,
    val urls: UnsplashUrl
) {
    val alt = description?.take(20) ?: "Unsplash image of a bird taken by ${user.name}"
}

@Serializable
data class UnsplashUser(val username: String, val name: String) {
    val attribution = "https://unsplash.com/@$username?utm_source=random_bird&utm_medium=referral"
}

@Serializable
data class UnsplashUrl(val regular: String)