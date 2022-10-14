package dev.bogwalk.plugins

import dev.bogwalk.models.UnsplashClient
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*

fun Application.configureRouting(client: UnsplashClient) {
    routing {
        static("/") {
            resources("css")
        }
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }
        get("/recent") {
            call.respond(FreeMarkerContent("recent.ftl", mapOf("photos" to client.fetchRecentBirds())))
        }
        get("random") {
            call.respond(FreeMarkerContent("random.ftl", mapOf("photo" to client.fetchRandomBird())))
        }
    }
}