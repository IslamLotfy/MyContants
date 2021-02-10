package com.example.mycontants.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mycontants.data.Constants
import com.example.mycontants.databinding.ActivityMainBinding
import com.example.mycontants.model.ContactModel
import com.example.mycontants.service.ContactWatchService
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    private val contactsList: MutableList<ContactModel> = mutableListOf()
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        lifecycle.addObserver(mainViewModel)
        loadContacts()
        observeOnContactsList()
        observeOnLastUpdate()
        startService(Intent(baseContext, ContactWatchService::class.java))
    }

    private fun observeOnLastUpdate() {
        mainViewModel.getLastUpdate()
        mainViewModel.lastUpdate.observe(this, Observer {
            applicationContext.getSharedPreferences(Constants.SharedPrefName, Context.MODE_PRIVATE)
                .edit().putLong(Constants.lastUpdate, it).apply()
            Log.e("lastupdate",it.toString())
        })
    }

    private fun observeOnContactsList() {
        mainViewModel.contactsList.observe(this, Observer {
            contactsAdapter = ContactsAdapter(it)
            mainActivityBinding.rvContacts.adapter = contactsAdapter
        })
    }

    private fun loadContacts() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            //callback onRequestPermissionsResult
        } else {
            mainViewModel.getContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }
}