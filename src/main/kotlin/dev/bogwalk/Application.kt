package dev.bogwalk

import dev.bogwalk.models.UnsplashClient
import io.ktor.server.application.*
import dev.bogwalk.plugins.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@Suppress("unused")
fun Application.module() {
    val unsplashClient = UnsplashClient(Java.create())

    environment.monitor.subscribe(ApplicationStopped) { application ->
        application.environment.log.info("Server is stopped")
        // release client resources
        unsplashClient.cleanUp()
        application.environment.log.info("HTTP client is closed")
        application.environment.monitor.unsubscribe(ApplicationStopped) {}
    }

    configureTemplating()
    configureRouting(unsplashClient)
}