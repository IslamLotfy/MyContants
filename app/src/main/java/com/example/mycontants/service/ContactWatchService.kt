package com.example.mycontants.service

import android.content.Intent
import android.os.*
import android.provider.ContactsContract
import android.util.Log
import com.example.mycontants.data.repository.ContactRepository
import dagger.android.AndroidInjection
import dagger.android.DaggerService
import javax.inject.Inject


class ContactWatchService : DaggerService() {
    private lateinit var mServiceLooper: Looper
    private lateinit var mServiceHandler: ServiceHandler

    @Inject
    lateinit var repository: ContactRepository

    private inner class ServiceHandler(mServiceLooper: Looper) : Handler(mServiceLooper) {
        override fun handleMessage(msg: Message) {
            try {
                startContactObserver()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startContactObserver() {
        try {
            application.contentResolver.registerContentObserver(
                ContactsContract.Contacts.CONTENT_URI, true, ContObserver(
                    Handler(mServiceLooper),
                    applicationContext,
                    repository
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        val thread = HandlerThread("ServiceStartArguments")
        thread.start()
        mServiceLooper = thread.looper
        mServiceHandler = ServiceHandler(mServiceLooper)
        AndroidInjection.inject(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val msg = mServiceHandler.obtainMessage()
        msg.arg1 = startId
        mServiceHandler.sendMessage(msg)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            //Code below is commented.
            //Turn it on if you want to run your service even after your app is closed
//            val intent = Intent(applicationContext, ContactWatchService::class.java)
//            startService(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}