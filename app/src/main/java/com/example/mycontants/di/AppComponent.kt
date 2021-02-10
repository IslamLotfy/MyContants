package com.example.mycontants.di

import android.app.Application
import com.example.mycontants.app.MyContacts
import com.example.mycontants.data.database.ContactsDatabaseModule
import com.example.mycontants.data.repository.ContactRepository
import com.example.mycontants.data.repository.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactory::class,
        ActivityBuildersModule::class,
        AppModule::class,
        RepositoryModule::class,
        ContactsDatabaseModule::class
    ]
)
interface AppComponent : AndroidInjector<MyContacts> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}