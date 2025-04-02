package com.example.contacts.views.controls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.models.Contact
import com.example.contacts.models.ContactDao
import kotlinx.coroutines.launch

class ContactControlViewModel(
    private val contactId: Long? = null,
    private val contactDao: ContactDao
) : ViewModel() {
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact> get() = _contact

    private val _navigateToContacts = MutableLiveData<Boolean>()
    val navigateToContacts: LiveData<Boolean> get() = _navigateToContacts

    init {
        viewModelScope.launch {
            _contact.value = if (contactId == null) Contact() else contactDao.get(contactId)
        }
    }

    fun onSaveContact() {
        val correctContact = _contact.value ?: return

        viewModelScope.launch {
            if (contactId == null) {
                contactDao.insert(correctContact)
            } else {
                contactDao.update(correctContact)
            }

            _navigateToContacts.value = true
        }
    }

    fun onClickBackButton(){
        _navigateToContacts.value = true
    }

    fun onNavigationCompleted() {
        _navigateToContacts.value = false
    }
}