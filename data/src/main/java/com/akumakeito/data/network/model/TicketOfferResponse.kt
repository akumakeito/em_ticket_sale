package com.akumakeito.data.network.model

import com.google.gson.annotations.SerializedName

data class TicketOfferResponse(
    @SerializedName("tickets_offers")
    val ticketsOffers: List<TicketOfferDTO>
)