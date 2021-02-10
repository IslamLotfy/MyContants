package com.example.mycontants.data.repository

import android.app.Application
import android.content.ContentResolver
import com.example.mycontants.data.database.ContactsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideContentResolver(application: Application) = application.contentResolver

    @Singleton
    @Provides
    fun provideRepository(contentResolver: ContentResolver,contactsDao: ContactsDao): ContactRepository{
        return LocalContactRepository(contentResolver = contentResolver,contactsDao = contactsDao)
    }
}