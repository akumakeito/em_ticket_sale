package com.akumakeito.domain.model




data class Ticket(
    val id: Int,
    val badge: String? = null,
    val price: Price,
    val company: String,
    val departure: Departure,
    val arrival: Arrival,
    val hasTransfer: Boolean,
)



