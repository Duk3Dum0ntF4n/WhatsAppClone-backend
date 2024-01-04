package com.ocean.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val text: String,
    val username: String,
    val id: String
)
