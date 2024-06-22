package com.akumakeito.domain.repository

import com.akumakeito.domain.state.ResultState
import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.domain.model.Ticket
import com.akumakeito.domain.model.TicketOffer
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun getMusicOffers() : ResultState<Flow<MusicOffer>>
    suspend fun getTicketOffers() : ResultState<Flow<TicketOffer>>
    suspend fun getTickets() : ResultState<Flow<Ticket>>

}