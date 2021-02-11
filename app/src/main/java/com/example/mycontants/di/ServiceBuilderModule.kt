package com.example.mycontants.di

import com.example.mycontants.data.repository.RepositoryModule
import com.example.mycontants.service.ContactWatchService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun bindsContactWatchService(): ContactWatchService
}