package com.example.mycontants.data.repository

import com.example.mycontants.data.database.Contact
import com.example.mycontants.utils.replace

class FakeContactDataSource(private var contactsList: MutableList<Contact>) : ContactsDataSource {
    override suspend fun getAllContacts(): List<Contact> {
       return contactsList
    }

    override suspend fun insertContacts(vararg contacts: Contact) {
        contactsList.addAll(contacts)
    }

    override suspend fun updateContact(contact: Contact): Int {
        contactsList = contactsList.replace(contact){
            it.contactId == contact.contactId
        }
        return 1
    }

    override suspend fun getLastUpdateDate(): String {
        contactsList.sortBy { it.lastUpdate }
        return contactsList.last().lastUpdate.toString()
    }

    override suspend fun deleteContact(contact: Contact): Int {
        contactsList.remove(contact)
        return 1
    }
}