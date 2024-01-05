package com.ocean

import com.ocean.connection.routing.configureChatRouting
import com.ocean.connection.routing.configureMessageRouting
import com.ocean.connection.routing.configureUserRouting
import com.ocean.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

fun main() {

    Database.connect(
        url = "jdbc:postgresql://localhost:5432/akizi",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )

    embeddedServer(
        CIO,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureUserRouting()
    configureChatRouting()
    configureMessageRouting()
}
