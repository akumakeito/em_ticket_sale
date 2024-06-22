package com.akumakeito.data.repository

import com.akumakeito.data.network.ApiService
import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.domain.model.Ticket
import com.akumakeito.domain.model.TicketOffer
import com.akumakeito.domain.repository.TicketRepository
import com.akumakeito.domain.state.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TicketRepository {
    override suspend fun getMusicOffers(): ResultState<Flow<MusicOffer>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTicketOffers(): ResultState<Flow<TicketOffer>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTickets(): ResultState<Flow<Ticket>> {
        TODO("Not yet implemented")
    }
}