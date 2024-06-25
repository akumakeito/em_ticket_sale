package com.akumakeito.data

import com.akumakeito.data.network.model.MusicOfferDTO
import com.akumakeito.data.network.model.TicketDTO
import com.akumakeito.data.network.model.TicketOfferDTO
import com.akumakeito.domain.model.Arrival
import com.akumakeito.domain.model.Departure
import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.domain.model.Price
import com.akumakeito.domain.model.Ticket
import com.akumakeito.domain.model.TicketOffer
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapMusicOfferFromDtoToModel(dto: MusicOfferDTO): MusicOffer {
        return MusicOffer(
            dto.id,
            dto.title,
            dto.town,
            Price(
                dto.price.value
            )
        )
    }


    fun mapTicketOfferFromDtoToModel(dto: TicketOfferDTO): TicketOffer {
        return TicketOffer(
            dto.id,
            dto.airlineTitle,
            dto.timeRange,
            Price(
                dto.price.value
            )
        )

    }

    fun mapTicketFromDtoToModel(dto: TicketDTO): Ticket {
        return Ticket(
            dto.id,
            dto.badge,
            Price(value = dto.price.value),
            dto.company,
            Departure(
                dto.departure.town,
                dto.departure.date,
                dto.departure.airport
            ),
            Arrival(
                dto.arrival.town,
                dto.arrival.date,
                dto.arrival.airport
            ),
            dto.hasTransfer
        )

    }
}