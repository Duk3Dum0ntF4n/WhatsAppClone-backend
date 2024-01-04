package com.ocean.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDTO(
    val username: String,
    val id: String
)
