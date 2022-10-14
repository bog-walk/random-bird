package dev.bogwalk.models

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class UnsplashClient(engine: HttpClientEngine) {
    private val accessKey = "YOUR-ACCESS-KEY"
    private val clientID = "Client-ID"

    private val apiUrl = "https://api.unsplash.com"
    private val randomUrl = "$apiUrl/photos/random"
    private val searchUrl = "$apiUrl/search/photos"

    private val birdQuery = "query" to "Bird"
    private val orderQuery = "order_by" to "latest"

    private val client = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            header(HttpHeaders.Authorization, "$clientID $accessKey")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    fun cleanUp() = client.close()

    /**
     * Returns the 3 most liked images from a list of 10 most recently created images.
     */
    suspend fun fetchRecentBirds(): List<UnsplashPhoto> {
        val data: UnsplashData = client.get(searchUrl) {
            parameter(birdQuery.first, birdQuery.second)
            parameter(orderQuery.first, orderQuery.second)
        }.body()
        // no need to use sortedWith() because sortedByDescending() is stable & will preserve
        // the original order by creation date if multiple images have equal likes
        return data.results.sortedByDescending(UnsplashPhoto::likes).take(3)
    }

    /**
     * Returns a single random image of any orientation.
     */
    suspend fun fetchRandomBird(): UnsplashPhoto = client.get(randomUrl) {
        parameter(birdQuery.first, birdQuery.second)
    }.body()
}