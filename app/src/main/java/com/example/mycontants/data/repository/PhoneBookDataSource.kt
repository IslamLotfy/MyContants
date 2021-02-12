package com.example.mycontants.data.repository

import android.net.Uri
import com.example.mycontants.data.database.Contact

interface PhoneBookDataSource {
    fun loadContactsFromPhoneBook(
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        uri: Uri?
    ): List<Contact>
}