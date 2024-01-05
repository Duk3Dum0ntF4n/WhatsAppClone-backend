package com.ocean.connection.routing

import com.ocean.database.User
import com.ocean.connection.remote.UserResponseRemote
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureUserRouting() {
    routing {
        findUserRoute()
    }
}

fun Route.findUserRoute() {
    get("/user/{username}") {
        val username = call.parameters["username"]
            ?: return@get call.respondText(
                "Empty user field",
                status = HttpStatusCode.BadRequest
            )
        val result = User.findUser(username)
            ?: return@get call.respondText(
                "No such user",
                status = HttpStatusCode.NotFound
            )
        call.respond(UserResponseRemote(username))
    }
}