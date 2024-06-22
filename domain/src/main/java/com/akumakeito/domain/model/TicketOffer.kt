package com.akumakeito.domain.model


data class TicketOffer(
    val id : Int,
    val airlineTitle : String,
    val timeRange : List<String>,
    val price : Price
)
