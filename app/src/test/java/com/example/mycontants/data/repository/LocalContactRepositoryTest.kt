package com.example.mycontants.data.repository

import com.example.mycontants.data.database.Contact
import com.example.mycontants.model.ContactModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class LocalContactRepositoryTest {
    private var contact1 = Contact(
        contactId = "ID1",
        contactName = "Contact 1",
        contactPhoneNumber = "024239871",
        lastUpdate = 131651351321
    )
    private var contact2 = Contact(
        contactId = "ID2",
        contactName = "Contact 2",
        contactPhoneNumber = "024239872",
        lastUpdate = 131651351321
    )
    private var contact3 = Contact(
        contactId = "ID3",
        contactName = "Contact 3",
        contactPhoneNumber = "024239873",
        lastUpdate = 131651351321
    )


    private var contactModel1 = ContactModel(
        contactId = "ID1",
        contactName = "Contact 1",
        contactPhoneNumber = "024239871"
    )
    private var contactModel2 = ContactModel(
        contactId = "ID2",
        contactName = "Contact 2",
        contactPhoneNumber = "024239872"
    )
    private var contactModel3 = ContactModel(
        contactId = "ID3",
        contactName = "Contact 3",
        contactPhoneNumber = "024239873"
    )

    lateinit var localContactsDataSource: ContactsDataSource
    lateinit var contactsPhoneBookDataSource: PhoneBookDataSource
    lateinit var contactRepository: ContactRepository

    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        localContactsDataSource = FakeContactDataSource(mutableListOf())
        contactsPhoneBookDataSource =
            FakePhoneBookDataSource(mutableListOf(contact1, contact2, contact3))
        contactRepository = LocalContactRepository(
            phoneBookDataSource = contactsPhoneBookDataSource,
            contactsDataSource = localContactsDataSource,
            contactMapper = EntityToModelMapper(),
            dispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun getAllContacts_withEmptyDatabase() = runBlockingTest {
        val contactModelList = listOf(contactModel1, contactModel2, contactModel3)
        val resultContactList_fromRepo = contactRepository.getContacts()
        val contactList = listOf(contact1,contact2,contact3)
        val resultContactList_fromDataSource = localContactsDataSource.getAllContacts()
        assertThat(resultContactList_fromRepo.size == 3)
        assertThat(resultContactList_fromRepo).isEqualTo(contactModelList)

        assertThat(resultContactList_fromDataSource.size == 3)
        assertThat(resultContactList_fromDataSource).isEqualTo(contactList)

    }

    @Test
    fun getAllContacts_withFilledDatabase() = runBlockingTest {
        val localContactsDataSource =
            FakeContactDataSource(mutableListOf(contact1, contact2, contact3))
        val contactRepository =
            LocalContactRepository(
                phoneBookDataSource = contactsPhoneBookDataSource,
                contactsDataSource = localContactsDataSource,
                contactMapper = EntityToModelMapper(),
                dispatcher = Dispatchers.Unconfined
            )
        val contactList = listOf(contactModel1, contactModel2, contactModel3)
        val resultContactList = contactRepository.getContacts()
        assertThat(resultContactList.size == 3)
        assertThat(resultContactList).isEqualTo(contactList)
    }

    @Test
    fun updateContacts_withContactChanged() = runBlockingTest{
        val localContactsDataSource =
            FakeContactDataSource(mutableListOf(contact1, contact2, contact3))
        contact3= Contact(contact3.contactId,"Contact 3 edited",contact3.contactPhoneNumber,contact3.lastUpdate)

        contactsPhoneBookDataSource = FakePhoneBookDataSource(mutableListOf(contact1,contact2,contact3))

        val contactList = listOf(contact1, contact2, contact3)
        val contactRepository =
            LocalContactRepository(
                phoneBookDataSource = contactsPhoneBookDataSource,
                contactsDataSource = localContactsDataSource,
                contactMapper = EntityToModelMapper(),
                dispatcher = Dispatchers.Unconfined
            )
        contactRepository.updateContacts()
        val resultContactList = localContactsDataSource.getAllContacts()

        assertThat(resultContactList.size == 3)
        assertThat(resultContactList).isEqualTo(contactList)
    }


}