package com.akumakeito.ticketsales.screens.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akumakeito.domain.model.Ticket
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.databinding.CardTicketBinding
import com.akumakeito.ticketsales.util.formatTicketTime
import com.akumakeito.ticketsales.util.getTravelTime
import com.akumakeito.ticketsales.util.toFormattedPriceString

class TicketAdapter(
    private val resources: Resources
) : ListAdapter<Ticket, TicketViewHolder>(
    TicketDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = CardTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding, resources)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TicketViewHolder(
    private val binding: CardTicketBinding,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticket: Ticket) {
        binding.apply {
            if (ticket.badge != null) {
                tvBadge.text = ticket.badge
            } else {
                tvBadge.visibility = View.GONE
            }

            tvTicketPrice.text = resources.getString(
                R.string.ticket_price,
                ticket.price.value.toFormattedPriceString()
            )
            tvTicketTime.text = resources.getString(
                R.string.ticket_time,
                formatTicketTime(ticket.departure.date),
                formatTicketTime(ticket.arrival.date)
            )

            tvTimeInRoad.text = resources.getString(
                R.string.time_in_road,
                getTravelTime(ticket.departure.date, ticket.arrival.date),
                if (ticket.hasTransfer) "" else resources.getString(R.string.no_transfer)
            )
            tvDepartureAirport.text = ticket.departure.airport
            tvArrivalAirport.text = ticket.arrival.airport


        }
    }

}

class TicketDiffCallback : DiffUtil.ItemCallback<Ticket>() {
    override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
        return oldItem == newItem
    }

}


