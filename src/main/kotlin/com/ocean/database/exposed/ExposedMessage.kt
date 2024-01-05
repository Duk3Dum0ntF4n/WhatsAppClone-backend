package com.ocean.database.exposed

import kotlinx.serialization.Serializable

@Serializable
data class ExposedMessage(
    val username: String,
    val text: String,
    val chatId: String
)
