package com.akumakeito.domain.repository

import com.akumakeito.domain.state.AppState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTicketOffersUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke() = flow {
        emit(AppState.Loading)
        try {
            val result = repository.getTicketOffers()
            emit(result)
        } catch (e: Exception) {
            emit(AppState.Error)
        }
    }


}