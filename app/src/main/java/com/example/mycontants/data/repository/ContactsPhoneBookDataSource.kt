package com.example.mycontants.data.repository

import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract
import com.example.mycontants.data.database.Contact
import javax.inject.Inject

class ContactsPhoneBookDataSource @Inject constructor(private val contentResolver: ContentResolver) :
    PhoneBookDataSource {
    override fun loadContactsFromPhoneBook(
        selection: String?,
        selectionArgs: Array<String>?,
        uri: Uri?
    ): List<Contact> {
        val contactsList: MutableList<Contact> = mutableListOf()
        val cursor = contentResolver.query(
            uri!!, null, selection, selectionArgs,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                )).toInt()
                val lastUpdated =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP))
                        .toLong()

                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        arrayOf(id),
                        null
                    )

                    if (cursorPhone!!.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            contactsList.add(
                                Contact(
                                    contactId = id,
                                    contactName = name,
                                    contactPhoneNumber = phoneNumValue,
                                    lastUpdate = lastUpdated
                                )
                            )
                        }
                    }
                    cursorPhone.close()
                }
            }
        } else {
            //   toast("No contacts available!")
        }
        cursor!!.close()
        return contactsList
    }
}