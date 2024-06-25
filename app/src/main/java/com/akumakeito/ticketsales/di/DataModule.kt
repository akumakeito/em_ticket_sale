package com.akumakeito.ticketsales.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.akumakeito.data.network.ApiFactory
import com.akumakeito.data.network.ApiService
import com.akumakeito.data.repository.TicketRepositoryImpl
import com.akumakeito.domain.repository.TicketRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

private const val DESTINATIONS = "destination"

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindTicketRepository(ticketRepositoryImpl: TicketRepositoryImpl): TicketRepository

    companion object {
        @Provides
        @ApplicationScope
        fun providesApiService() : ApiService {
            return ApiFactory.apiService
        }

        @Provides
        @ApplicationScope
        fun providesSharedPrefs(application: Application): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(application)
        }
    }

}