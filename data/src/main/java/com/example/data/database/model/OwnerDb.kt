package com.example.data.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerDb(
    @SerialName(value = "userName")
    val userName: String,
    @SerialName(value = "avatarUrl")
    val avatarUrl: String?
)
