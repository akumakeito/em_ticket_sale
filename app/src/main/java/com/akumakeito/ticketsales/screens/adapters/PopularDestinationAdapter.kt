package com.akumakeito.ticketsales.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akumakeito.ticketsales.databinding.CardPopularDestinationsBinding
import com.akumakeito.ticketsales.util.PopularDestination

interface OnInteractionListener {
    fun onClick(destination: PopularDestination)
}

class PopularDestinationAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<PopularDestination, PopularDestinationViewHolder>(PopularDestinationDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularDestinationViewHolder {
        val binding = CardPopularDestinationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularDestinationViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PopularDestinationViewHolder, position: Int) {
        val destination = getItem(position)
        holder.bind(destination)
    }
}

class PopularDestinationViewHolder(
    private val binding: CardPopularDestinationsBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(destination: PopularDestination) {
        binding.apply {
            tvDestination.text = destination.destination
            ivImage.setImageResource(destination.image)
            destinationContainer.setOnClickListener {
                onInteractionListener.onClick(destination)
            }
        }
    }
}

class PopularDestinationDiffCallback : DiffUtil.ItemCallback<PopularDestination>() {
    override fun areItemsTheSame(
        oldItem: PopularDestination,
        newItem: PopularDestination
    ): Boolean {
        return oldItem.destination == newItem.destination
    }

    override fun areContentsTheSame(
        oldItem: PopularDestination,
        newItem: PopularDestination
    ): Boolean {
        return oldItem == newItem
    }

}
