package com.akumakeito.domain.repository

import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.domain.state.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMusicOffersUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(): Flow<AppState<List<MusicOffer>>> = flow {
        emit(AppState.Loading)
        try {
            val result = repository.getMusicOffers()
            emit(result)
        } catch (e: Exception) {
            emit(AppState.Error)
        }
    }
}