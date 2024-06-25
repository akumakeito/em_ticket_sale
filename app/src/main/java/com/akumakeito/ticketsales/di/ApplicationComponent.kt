package com.akumakeito.ticketsales.di

import android.app.Application
import com.akumakeito.ticketsales.screens.MainActivity
import com.akumakeito.ticketsales.screens.stub.ComplexRouteFragment
import com.akumakeito.ticketsales.screens.stub.EverywhereFragment
import com.akumakeito.ticketsales.screens.stub.HotSalesFragment
import com.akumakeito.ticketsales.screens.stub.HotelsFragment
import com.akumakeito.ticketsales.screens.stub.ProfileFragment
import com.akumakeito.ticketsales.screens.stub.ShortsFragment
import com.akumakeito.ticketsales.screens.stub.SubscribesFragment
import com.akumakeito.ticketsales.screens.stub.WeekendsFragment
import com.akumakeito.ticketsales.screens.tickets.MainFragment
import com.akumakeito.ticketsales.screens.tickets.SearchFragment
import com.akumakeito.ticketsales.screens.tickets.TicketFeedFragment
import com.akumakeito.ticketsales.screens.tickets.TicketsPreviewFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: MainFragment)

    fun inject(fragment: SearchFragment)

    fun inject(fragment: TicketsPreviewFragment)
    fun inject(fragment: TicketFeedFragment)


    fun inject(fragment: ComplexRouteFragment)
    fun inject(fragment: EverywhereFragment)
    fun inject(fragment: WeekendsFragment)
    fun inject(fragment: HotSalesFragment)
    fun inject(fragment: HotelsFragment)
    fun inject(fragment: ShortsFragment)
    fun inject(fragment: SubscribesFragment)
    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}