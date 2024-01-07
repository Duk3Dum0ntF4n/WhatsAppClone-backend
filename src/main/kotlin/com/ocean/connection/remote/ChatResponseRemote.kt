package com.ocean.connection.remote

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponseRemote(
    val username: String,
    val chatId: String
)