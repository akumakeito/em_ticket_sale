package com.akumakeito.data.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class MusicOfferResponse(
    @SerializedName("offers")
    val musicOffers: List<MusicOfferDTO>
)
