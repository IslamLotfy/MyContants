package com.example.mycontants.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey
    val contactId: String,
    @ColumnInfo(name = "contact_name")
    val contactName: String,
    @ColumnInfo(name = "contact_phone_number")
    val contactPhoneNumber: String
)