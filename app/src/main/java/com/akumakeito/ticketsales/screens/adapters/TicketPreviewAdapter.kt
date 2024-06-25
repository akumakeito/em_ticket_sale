package com.akumakeito.ticketsales.screens.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akumakeito.domain.model.TicketOffer
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.databinding.CardTicketsPreviewBinding
import com.akumakeito.ticketsales.util.toFormattedPriceString

class TicketPreviewAdapter(private val resources: Resources) :
    ListAdapter<TicketOffer, TicketPreviewViewHolder>(TicketPreviewDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketPreviewViewHolder {
        val binding =
            CardTicketsPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketPreviewViewHolder(binding, resources)
    }

    override fun onBindViewHolder(holder: TicketPreviewViewHolder, position: Int) {
        val color = when (position % 3) {
            0 -> R.color.red
            1 -> R.color.blue
            else -> R.color.white
        }
        holder.bind(getItem(position), color)
    }
}

class TicketPreviewViewHolder(
    private val binding: CardTicketsPreviewBinding,
    private val resources: Resources

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticketOffer: TicketOffer, @ColorRes color: Int) {
        binding.apply {
            tvTicketPrice.text = resources.getString(
                R.string.ticket_price,
                ticketOffer.price.value.toFormattedPriceString()
            )
            tvTicketTimes.text = ticketOffer.timeRange.joinToString("  ")
            tvAirlineName.text = ticketOffer.airlineTitle
            ivTicketLogo.imageTintList = resources.getColorStateList(color, null)
        }
    }
}


class TicketPreviewDiffCallback : DiffUtil.ItemCallback<TicketOffer>() {
    override fun areItemsTheSame(oldItem: TicketOffer, newItem: TicketOffer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TicketOffer, newItem: TicketOffer): Boolean {
        return oldItem == newItem
    }

}
