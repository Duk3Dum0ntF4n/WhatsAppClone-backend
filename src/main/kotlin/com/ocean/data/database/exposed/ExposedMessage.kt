package com.ocean.data.database.exposed

data class ExposedMessage(
    val id: String,
    val sender: String,
    val receiver: String,
    val text: String
)
