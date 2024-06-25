package com.akumakeito.ticketsales.screens.tickets

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.domain.model.Destinations
import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.domain.model.Ticket
import com.akumakeito.domain.model.TicketOffer
import com.akumakeito.domain.repository.GetDestinationsUseCase
import com.akumakeito.domain.repository.GetMusicOffersUseCase
import com.akumakeito.domain.repository.GetTicketOffersUseCase
import com.akumakeito.domain.repository.GetTicketsUseCase
import com.akumakeito.domain.repository.SaveDestinationsUseCase
import com.akumakeito.domain.state.AppState
import com.akumakeito.ticketsales.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketViewModel @Inject constructor(
    private val getMusicOffersUseCase: GetMusicOffersUseCase,
    private val saveDestinationsUseCase: SaveDestinationsUseCase,
    private val getDestinationsUseCase: GetDestinationsUseCase,
    private val getTicketOffersUseCase: GetTicketOffersUseCase,
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {

    private val _musicOffersState = MutableStateFlow<AppState<List<MusicOffer>>>(AppState.Loading)
    val musicOffersState = _musicOffersState.asStateFlow()

    private val _ticketOffersState = MutableStateFlow<AppState<List<TicketOffer>>>(AppState.Loading)
    val ticketOffersState = _ticketOffersState.asStateFlow()

    private val _ticketState = MutableStateFlow<AppState<List<Ticket>>>(AppState.Loading)
    val ticketState = _ticketState.asStateFlow()

    private val _destination = MutableStateFlow(Destinations())
    val destination = _destination.asStateFlow()


    private val _departureDate = MutableStateFlow(Calendar.getInstance().timeInMillis)
    val departureDate = _departureDate.asStateFlow()

    private val _arrivalDate = MutableStateFlow<Long?>(null)
    val arrivalDate = _arrivalDate.asStateFlow()


    init {
        getMusicOffers()
        getDestinations()
    }

    fun setDepartureDate(departureDate: Long) {
        _departureDate.value = departureDate
    }

    fun setArrivalDate(arrivalDate: Long) {
        _arrivalDate.value = arrivalDate
    }

    private fun getMusicOffers() = viewModelScope.launch {
        getMusicOffersUseCase().collect { flowState ->
            if (flowState is AppState.Success) {
                val updatedData = updateMusicOffersWithImages(flowState.data)
                _musicOffersState.value = AppState.Success(updatedData)
            } else {
                _musicOffersState.value = flowState
            }

            Log.d("TicketViewModel", "getMusicOffers: ${musicOffersState.value}")
        }
    }

    fun getTicketOffers() = viewModelScope.launch {
        getTicketOffersUseCase().collect {
            _ticketOffersState.value = it
            Log.d("TicketViewModel", "getTicketOffers: ${ticketOffersState.value}")
        }
    }

    fun getTickets() = viewModelScope.launch {
        getTicketsUseCase().collect {
            _ticketState.value = it
            Log.d("TicketViewModel", "getTickets: ${ticketState.value}")
        }
    }

    private fun updateMusicOffersWithImages(data: List<MusicOffer>): List<MusicOffer> {
        return data.map { musicOffer ->
            val imageId = when (musicOffer.id) {
                1 -> R.drawable.image_die_antwood
                2 -> R.drawable.image_socrat_lera
                3 -> R.drawable.image_lampabict
                else -> null
            }
            Log.d("TicketViewModel", "Updating image for id ${musicOffer.id}: $imageId")
            musicOffer.copy(image = imageId)
        }
    }

    fun reverseDestinations() {
        val currentFromDest = _destination.value.fromDest ?: ""
        val currentToDest = _destination.value.toDest ?: ""

        setFromDest(currentToDest)
        setToDest(currentFromDest)
    }

    fun setFromDest(fromDest: String) = viewModelScope.launch {
        _destination.update {
            it.copy(fromDest = fromDest)
        }
        delay(2000)
        destination.value.fromDest?.let { saveDestinationsUseCase(it) }


        Log.d("Destinations", "setFromDest: after ${destination.value}")
    }

    fun setToDest(toDest: String) = viewModelScope.launch {
        _destination.update {
            it.copy(toDest = toDest)
        }

        Log.d("Destinations", "setToDest: after ${destination.value}")

    }

    private fun getDestinations() = viewModelScope.launch {
        _destination.update {
            it.copy(fromDest = getDestinationsUseCase())
        }

        Log.d("Destinations", "getDestinations: ${destination.value}")
    }
}