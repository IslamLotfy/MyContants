package com.example.mycontants.data.repository

import com.example.mycontants.data.database.Contact
import com.example.mycontants.data.database.ContactsDao
import javax.inject.Inject

class LocalContactsDataSource @Inject constructor(private val contactsDao: ContactsDao) : ContactsDataSource {
    override suspend fun getAllContacts(): List<Contact> {
        return contactsDao.getAllContacts()
    }

    override suspend fun insertContacts(vararg contacts: Contact) {
        return contactsDao.insertContacts(*contacts)
    }

    override suspend fun updateContact(contact: Contact): Int {
        return contactsDao.updateContact(contact)
    }

    override suspend fun getLastUpdateDate(): String {
        return contactsDao.getLastUpdateDate().toString()
    }

    override suspend fun deleteContact(contact: Contact): Int {
        return contactsDao.deleteContact(contactID = contact.contactId,contactName = contact.contactName)
    }
}