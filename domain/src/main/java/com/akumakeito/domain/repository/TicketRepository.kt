package com.akumakeito.domain.repository

import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.domain.model.Ticket
import com.akumakeito.domain.model.TicketOffer
import com.akumakeito.domain.state.AppState

interface TicketRepository {
    suspend fun getMusicOffers(): AppState<List<MusicOffer>>
    suspend fun getTicketOffers(): AppState<List<TicketOffer>>
    suspend fun getTickets(): AppState<List<Ticket>>

    suspend fun saveDestinations(fromDest: String)
    suspend fun getDestinations(): String?


}