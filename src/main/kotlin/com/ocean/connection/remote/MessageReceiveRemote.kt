package com.ocean.connection.remote

import kotlinx.serialization.Serializable

@Serializable
data class MessageReceiveRemote(
    val text: String,
    val chatId: String
)
