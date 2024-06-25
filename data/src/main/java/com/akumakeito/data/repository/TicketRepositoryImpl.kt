package com.akumakeito.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.akumakeito.data.Mapper
import com.akumakeito.data.network.ApiService
import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.domain.model.Ticket
import com.akumakeito.domain.model.TicketOffer
import com.akumakeito.domain.repository.TicketRepository
import com.akumakeito.domain.state.AppState
import javax.inject.Inject

private const val FROM_DESTINATION = "from_destination"

class TicketRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: SharedPreferences,
    private val mapper: Mapper
) : TicketRepository {

    override suspend fun getMusicOffers(): AppState<List<MusicOffer>> {
        return try {
            val response = apiService.getMusicOffers()

            if (response.isSuccessful) {
                val body = response.body() ?: return AppState.Error

                val data = body.musicOffers.map {
                    mapper.mapMusicOfferFromDtoToModel(it)
                }

                AppState.Success(data)
            } else {
                AppState.Error
            }

        } catch (e: Exception) {
            AppState.Error
        }
    }

    override suspend fun getTicketOffers(): AppState<List<TicketOffer>> {
        return try {
            val response = apiService.getTicketOffers()

            if (response.isSuccessful) {
                val body = response.body() ?: return AppState.Error
                val data = body.ticketsOffers.map {
                    mapper.mapTicketOfferFromDtoToModel(it)
                }
                AppState.Success(data)
            } else {
                AppState.Error
            }
        } catch (e: Exception) {
            AppState.Error
        }
    }

    override suspend fun getTickets(): AppState<List<Ticket>> {
        return try {
            val response = apiService.getTickets()

            if (response.isSuccessful) {
                val body = response.body() ?: return AppState.Error
                val data = body.tickets.map {
                    mapper.mapTicketFromDtoToModel(it)
                }
                AppState.Success(data)
            } else {
                AppState.Error
            }
        } catch (e: Exception) {
            AppState.Error
        }
    }

    override suspend fun saveDestinations(fromDest: String) {
        prefs.edit {
            putString(FROM_DESTINATION, fromDest)
        }
    }

    override suspend fun getDestinations(): String? {
        return prefs.getString(FROM_DESTINATION, null)
    }
}