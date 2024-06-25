package com.akumakeito.ticketsales.screens.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akumakeito.domain.model.MusicOffer
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.databinding.CardFlyToMusicBinding
import com.akumakeito.ticketsales.util.toFormattedPriceString

class MusicOfferAdapter(
    private val resources: Resources
) : ListAdapter<MusicOffer, MusicOfferViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicOfferViewHolder {
        val binding =
            CardFlyToMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicOfferViewHolder(binding, resources)
    }

    override fun onBindViewHolder(holder: MusicOfferViewHolder, position: Int) {
        val musicOffer = getItem(position)
        holder.bind(musicOffer)
    }
}

class MusicOfferViewHolder(
    private val binding: CardFlyToMusicBinding,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(musicOffer: MusicOffer) {
        binding.apply {
            tvArtistName.text = musicOffer.title
            tvCity.text = musicOffer.town
            tvTicketPrice.text = resources.getString(
                R.string.ticket_price,
                musicOffer.price.value.toFormattedPriceString()
            )
            ivArtistImage.setImageResource(musicOffer.image ?: R.drawable.search_background_shape)
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<MusicOffer>() {
    override fun areItemsTheSame(oldItem: MusicOffer, newItem: MusicOffer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MusicOffer, newItem: MusicOffer): Boolean {
        return oldItem == newItem
    }
}
