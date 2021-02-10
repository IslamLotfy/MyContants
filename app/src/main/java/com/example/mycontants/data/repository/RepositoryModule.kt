package com.example.mycontants.data.repository

import android.app.Application
import android.content.ContentResolver
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideContentResolver(application: Application) = application.contentResolver

    @Singleton
    @Provides
    fun provideRepository(contentResolver: ContentResolver): ContactRepository{
        return LocalContactRepository(contentResolver = contentResolver)
    }
}