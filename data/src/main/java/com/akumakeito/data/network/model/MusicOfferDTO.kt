package com.akumakeito.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MusicOfferDTO(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
)
