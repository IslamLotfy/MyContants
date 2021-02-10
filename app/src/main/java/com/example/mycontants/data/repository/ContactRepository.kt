package com.example.mycontants.data.repository

import com.example.mycontants.model.ContactModel
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun getContacts(): List<ContactModel>
    suspend fun getLastUpdateDate(): Long
}