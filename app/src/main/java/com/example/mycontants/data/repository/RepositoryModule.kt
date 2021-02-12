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
    fun provideContactsDataSource(contactsDao: ContactsDao): ContactsDataSource{
        return LocalContactsDataSource(contactsDao = contactsDao)
    }

    @Singleton
    @Provides
    fun providePhoneBookDataSource(contentResolver: ContentResolver): PhoneBookDataSource{
        return ContactsPhoneBookDataSource(contentResolver = contentResolver)
    }

    @Singleton
    @Provides
    fun provideRepository(phoneBookDataSource: PhoneBookDataSource,contactsDataSource: ContactsDataSource): ContactRepository{
        return LocalContactRepository(phoneBookDataSource = phoneBookDataSource,contactsDataSource = contactsDataSource)
    }
}