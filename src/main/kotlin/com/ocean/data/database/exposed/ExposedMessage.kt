package com.ocean.data.database.exposed

data class ExposedMessage(
    val sender: String,
    val receiver: String,
    val text: String
)
