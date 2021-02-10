package com.example.mycontants.data.repository

import com.example.mycontants.data.database.Contact
import com.example.mycontants.model.ContactModel

class EntityToModelMapper {
    fun mapFromEntityToModel(contact: Contact): ContactModel {
        return ContactModel(
            contactId = contact.contactId,
            contactName = contact.contactName,
            contactPhoneNumber = contact.contactPhoneNumber
        )
    }
}