package com.example.mycontants.data.repository

import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract
import com.example.mycontants.data.database.Contact
import com.example.mycontants.model.ContactModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalContactRepository @Inject constructor(
    private val phoneBookDataSource: PhoneBookDataSource,
    private val contactsDataSource: ContactsDataSource,
    private val contactMapper: EntityToModelMapper = EntityToModelMapper(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ContactRepository {

    override suspend fun getContacts(): List<ContactModel> = withContext(dispatcher) {
        var contactList = contactsDataSource.getAllContacts()
        if (contactList.isNullOrEmpty()) {
            contactsDataSource.insertContacts(
                contacts = phoneBookDataSource.loadContactsFromPhoneBook(
                    uri = ContactsContract.Contacts.CONTENT_URI
                ).toTypedArray()
            )
            contactList = contactsDataSource.getAllContacts()
        }
        return@withContext contactList.map {
            contactMapper.mapFromEntityToModel(contact = it)
        }
    }

    override suspend fun updateContacts() = withContext(dispatcher) {
        val lastUpdateFromDatabase = getLastUpdateDate()
        val updatedContacts = phoneBookDataSource.loadContactsFromPhoneBook(
            uri = ContactsContract.Contacts.CONTENT_URI,
            selection = ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP + ">=?",
            selectionArgs = arrayOf(lastUpdateFromDatabase)
        )
        updatedContacts.map {
            contactsDataSource.updateContact(it)
        }
        return@withContext
    }

    private suspend fun getLastUpdateDate(): String = withContext(dispatcher) {
        return@withContext contactsDataSource.getLastUpdateDate()
    }

}