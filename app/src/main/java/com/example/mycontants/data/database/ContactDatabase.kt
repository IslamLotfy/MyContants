package com.example.mycontants.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Contact::class],version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactsDao
}