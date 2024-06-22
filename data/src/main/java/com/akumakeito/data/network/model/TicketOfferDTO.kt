package com.akumakeito.data.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TicketOfferDTO(
    val id : Int,
    @SerializedName("title")
    val airlineTitle : String,
    @SerializedName("time_range")
    val timeRange : List<String>,
    val price : Price
)
