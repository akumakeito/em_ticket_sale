package com.akumakeito.domain.model

import androidx.annotation.DrawableRes


data class MusicOffer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price,
    @DrawableRes val image: Int? = null
)


