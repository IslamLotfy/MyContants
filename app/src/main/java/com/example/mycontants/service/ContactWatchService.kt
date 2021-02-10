package com.example.mycontants.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.provider.ContactsContract


class ContactWatchService : Service() {
    private lateinit var mServiceLooper: Looper
    private lateinit var mServiceHandler: ServiceHandler

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
                    applicationContext
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