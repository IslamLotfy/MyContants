package com.example.mycontants.service

import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.mycontants.data.Constants
import com.example.mycontants.data.database.Contact

class ContObserver(
    handler: Handler,
    private val applicationContext: Context,
) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        if (!selfChange && ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        )
            loadContact()
    }

    private fun loadContact(): List<Contact> {
        val lastUpdateFromDatabase =
            applicationContext.getSharedPreferences(Constants.SharedPrefName,Context.MODE_PRIVATE)
                .getLong(Constants.lastUpdate, 0).toString()
        val contentResolver = applicationContext.contentResolver
        val contactsList: MutableList<Contact> = mutableListOf()
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP + ">=?",
            arrayOf(lastUpdateFromDatabase),
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
        Log.e("from Observer",contactsList.size.toString())
        cursor!!.close()
        return contactsList
    }

}