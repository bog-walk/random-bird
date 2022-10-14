package dev.bogwalk.models

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object UnsplashClient {
    private const val ACCESS_KEY = "YOUR_ACCESS_KEY"
    private const val CLIENT_ID = "Client-ID"
    private const val API_URL = "https://api.unsplash.com"

    private const val RANDOM = "$API_URL/photos/random"
    private const val SEARCH = "$API_URL/search/photos"

    private const val QUERY = "query"
    private const val birds = "Bird"

    private val client: HttpClient = HttpClient(Java) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            header(HttpHeaders.Authorization, "$CLIENT_ID $ACCESS_KEY")
        }
    }

    // Response will have the 10 json objects most recently created (of varying orientation)
    suspend fun fetchRecentBirds(): List<UnsplashPhoto> {
        val data: UnsplashData = client.get(SEARCH) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            parameter(QUERY, birds)
            parameter("order_by", "latest")
        }.body()
        // Recently created photos may not have many likes
        // no need to use .sortedWith() because .sortedByDescending() is stable & will
        // preserve the original order by creation date
        return data.results.sortedByDescending(UnsplashPhoto::likes).take(3)
    }

    // Response will have only 1 json object
    suspend fun fetchRandomBird(): UnsplashPhoto = client.get(RANDOM) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        parameter(QUERY, birds)
    }.body()
}