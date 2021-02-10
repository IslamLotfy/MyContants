package com.example.mycontants.data.database

import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contact")
    suspend fun getAllContacts(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(vararg contacts: Contact)

    @Update
    suspend fun updateContact(contact: Contact): Int

    @Delete
    suspend fun deleteContact(contact: Contact): Int

}