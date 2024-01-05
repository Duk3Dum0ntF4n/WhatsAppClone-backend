package com.ocean.connection.remote

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseRemote(
    val username: String
)
