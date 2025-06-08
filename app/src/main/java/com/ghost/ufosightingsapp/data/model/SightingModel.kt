package com.ghost.ufosightingsapp.data.model

import java.util.UUID

enum class SightingType {
    BLOB, LAMPSHADE
}

data class SightingModel(
    val id: String = UUID.randomUUID().toString(),
    val date: String,
    val type: SightingType,
    val speed: Int
)