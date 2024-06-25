package com.akumakeito.ticketsales

import android.app.Application
import com.akumakeito.ticketsales.di.DaggerApplicationComponent

class TicketsApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}