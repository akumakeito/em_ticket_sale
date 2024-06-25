package com.akumakeito.domain.repository

import javax.inject.Inject

class GetDestinationsUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) {
    suspend operator fun invoke() = ticketRepository.getDestinations()

}