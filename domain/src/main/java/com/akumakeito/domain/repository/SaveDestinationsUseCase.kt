package com.akumakeito.domain.repository

import javax.inject.Inject

class SaveDestinationsUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) {

    suspend operator fun invoke(fromDest: String) {
        ticketRepository.saveDestinations(fromDest)
    }

}