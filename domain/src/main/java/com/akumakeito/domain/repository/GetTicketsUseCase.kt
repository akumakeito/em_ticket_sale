package com.akumakeito.domain.repository

import com.akumakeito.domain.state.AppState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke() = flow {
        emit(AppState.Loading)
        try {
            val result = repository.getTickets()
            emit(result)
        } catch (e: Exception) {
            emit(AppState.Error)
        }
    }

}