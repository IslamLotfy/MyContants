package com.example.mycontants.data.repository

import com.example.mycontants.data.database.Contact
import com.example.mycontants.model.ContactModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EntityToModelMapperTest{
    private var contact1 = Contact(
        contactId = "ID1",
        contactName = "Contact 1",
        contactPhoneNumber = "024239871",
        lastUpdate = 131651351321
    )
    private var contactModel1 = ContactModel(
        contactId = "ID1",
        contactName = "Contact 1",
        contactPhoneNumber = "024239871"
    )

    @Test
    fun mapFromEntityToModel(){
        val mapper = EntityToModelMapper()
        val resultModel = mapper.mapFromEntityToModel(contact1)
        assertThat(resultModel).isEqualTo(contactModel1)
    }
}