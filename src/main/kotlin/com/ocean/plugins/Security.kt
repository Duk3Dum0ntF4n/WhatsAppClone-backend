package com.ocean.plugins

import com.ocean.connection.session.MessengerSession
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.generateNonce

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<MessengerSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Plugins) {
        if(call.sessions.get<MessengerSession>() == null) {
            val username = call.parameters["username"] ?: "Empty"
            call.sessions.set(MessengerSession(username, generateNonce()))
        }
    }
}
