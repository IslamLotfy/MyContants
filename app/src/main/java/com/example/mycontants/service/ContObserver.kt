package com.example.mycontants.service

import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import androidx.core.app.ActivityCompat
import com.example.mycontants.data.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContObserver(
    handler: Handler,
    private val applicationContext: Context,
    private val repository: ContactRepository,
) : ContentObserver(handler) {


    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        if (!selfChange && ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val ioScope = CoroutineScope(Dispatchers.IO + Job())
            ioScope.launch {
                repository.updateContacts()
            }
        }
    }


}