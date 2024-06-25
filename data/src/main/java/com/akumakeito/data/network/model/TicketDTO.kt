package com.akumakeito.data.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TicketDTO(
    @SerializedName("arrival")
    val arrival: Arrival,
    @SerializedName("badge")
    val badge: String? = null,
    @SerializedName("company")
    val company: String,
    @SerializedName("departure")
    val departure: Departure,
    @SerializedName("hand_luggage")
    val handLuggage: HandLuggage,
    @SerializedName("has_transfer")
    val hasTransfer: Boolean,
    @SerializedName("has_visa_transfer")
    val hasVisaTransfer: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_exchangable")
    val isExchangable: Boolean,
    @SerializedName("is_returnable")
    val isReturnable: Boolean,
    @SerializedName("luggage")
    val luggage: Luggage,
    @SerializedName("price")
    val price: Price,
    @SerializedName("provider_name")
    val providerName: String
)

@Serializable
data class HandLuggage(
    @SerializedName("has_hand_luggage")
    val hasHandLuggage: Boolean,
    @SerializedName("size")
    val size: String
)

@Serializable
data class Luggage(
    @SerializedName("has_luggage")
    val hasLuggage: Boolean,
    @SerializedName("price")
    val price: Price
)

@Serializable
data class Arrival(
    val town: String,
    val date: String,
    val airport: String
)

@Serializable
data class Departure(
    val town: String,
    val date: String,
    val airport: String
)