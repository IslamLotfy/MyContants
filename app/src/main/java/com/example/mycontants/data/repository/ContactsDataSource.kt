package com.example.mycontants.data.repository

import com.example.mycontants.data.database.Contact

interface ContactsDataSource {
    suspend fun getAllContacts(): List<Contact>
    suspend fun insertContacts(vararg contacts: Contact)
    suspend fun updateContact(contact: Contact): Int
    suspend fun getLastUpdateDate(): String
    suspend fun deleteContact(contact: Contact): Int
}