package com.ocean.connection

import com.ocean.connection.remote.ChatResponseRemote
import io.ktor.websocket.WebSocketSession

data class Member(
    val username: String,
    val chatList: List<ChatResponseRemote>,
    val sessionId: String,
    val socket: WebSocketSession
)
