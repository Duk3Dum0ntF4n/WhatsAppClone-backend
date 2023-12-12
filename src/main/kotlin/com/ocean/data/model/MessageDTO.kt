package com.ocean.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val sender: String,
    val receiver: String,
    val text: String,
    val timestamp: Long
)
