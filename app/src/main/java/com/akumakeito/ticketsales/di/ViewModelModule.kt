package com.akumakeito.ticketsales.di

import androidx.lifecycle.ViewModel
import com.akumakeito.ticketsales.screens.tickets.TicketViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TicketViewModel::class)
    fun bindsTicketViewModel(ticketViewModel: TicketViewModel): ViewModel
}