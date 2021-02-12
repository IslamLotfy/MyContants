package com.example.mycontants.data.database

import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contact")
    suspend fun getAllContacts(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(vararg contacts: Contact)

    @Query("SELECT MAX(last_update) FROM contact")
    suspend fun getLastUpdateDate(): Long

    @Update
    suspend fun updateContact(contact: Contact): Int

    @Query("DELETE FROM contact WHERE contactId = :contactID OR contact_name = :contactName")
    suspend fun deleteContact(contactID: String, contactName: String): Int

}