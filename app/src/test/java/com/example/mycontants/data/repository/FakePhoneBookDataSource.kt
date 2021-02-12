package com.example.mycontants.data.repository

import android.net.Uri
import com.example.mycontants.data.database.Contact
import com.example.mycontants.utils.replace

class FakePhoneBookDataSource (private var contactsList: MutableList<Contact>) : PhoneBookDataSource {
    override fun loadContactsFromPhoneBook(
        selection: String?,
        selectionArgs: Array<String>?,
        uri: Uri?
    ): List<Contact> {
        return contactsList
    }

}