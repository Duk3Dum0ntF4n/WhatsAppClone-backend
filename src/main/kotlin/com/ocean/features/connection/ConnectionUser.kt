package com.ocean.features.connection

import io.ktor.websocket.WebSocketSession
import kotlinx.serialization.Serializable

@Serializable
data class ConnectionUser(
    val login: String,
    val sessionId: String,
    val socket: WebSocketSession
)
