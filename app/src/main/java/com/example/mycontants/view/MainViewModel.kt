package com.example.mycontants.view

import androidx.lifecycle.*
import com.example.mycontants.data.repository.ContactRepository
import com.example.mycontants.model.ContactModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val contactRepository: ContactRepository) :
    ViewModel(), LifecycleObserver {
    val contactsList = MutableLiveData<List<ContactModel>>()
    val lastUpdate = MutableLiveData<Long>()

    fun getContacts(){
        viewModelScope.launch(Dispatchers.IO){
            contactsList.postValue(contactRepository.getContacts())
        }
    }

    fun getLastUpdate(){
        viewModelScope.launch(Dispatchers.IO){
            lastUpdate.postValue(contactRepository.getLastUpdateDate())
        }
    }
}