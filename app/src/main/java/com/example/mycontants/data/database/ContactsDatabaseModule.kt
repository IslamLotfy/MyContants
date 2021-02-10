package com.example.mycontants.data.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContactsDatabaseModule {
    @Singleton
    @Provides
    fun provideContactsDatabase(application: Application): ContactDatabase {
        return Room.databaseBuilder(application, ContactDatabase::class.java, "Contacts.bd").build()
    }

    @Singleton
    @Provides
    fun provideContactsDao(contactDatabase: ContactDatabase) = contactDatabase.contactDao()
}