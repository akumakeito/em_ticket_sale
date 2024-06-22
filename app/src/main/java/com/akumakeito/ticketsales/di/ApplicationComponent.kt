package com.akumakeito.ticketsales.di

import android.app.Application
import com.akumakeito.ticketsales.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}