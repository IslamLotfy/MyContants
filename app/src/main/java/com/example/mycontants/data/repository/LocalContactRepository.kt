package com.example.mycontants.data.repository

import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import androidx.core.view.KeyEventDispatcher
import com.example.mycontants.model.ContactModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalContactRepository @Inject constructor(
    private val contentResolver: ContentResolver,
) :
    ContactRepository {
    override suspend fun getContacts(): List<ContactModel> {
        return (loadContact())

    }

    private fun loadContact(): List<ContactModel> {
        val contactsList: MutableList<ContactModel> = mutableListOf()
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null
        )

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                )).toInt()

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
                                ContactModel(
                                    contactId = id,
                                    contactName = name,
                                    contactPhoneNumber = phoneNumValue
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