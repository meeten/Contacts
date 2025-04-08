package com.example.contacts.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.models.Contact
import com.example.contacts.models.ContactDao
import kotlinx.coroutines.launch

class ContactsViewModel(private val contactDao: ContactDao) : ViewModel() {
    private var _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    private val _navigateToContact = MutableLiveData<ContactNavigateEvent?>()
    val navigateToContact: MutableLiveData<ContactNavigateEvent?> get() = _navigateToContact

    private val _deleteContact = MutableLiveData<Contact>()
    val deleteContact: LiveData<Contact> get() = _deleteContact

    init {
        loadContacts()
    }

    private fun loadContacts() {
        contactDao.getAll().observeForever { list -> _contacts.value = list }
    }

    fun onClickContact(contactId: Long) {
        _navigateToContact.value = ContactNavigateEvent.EditContactNavigate(contactId)
    }

    fun onClickLongContact(contact: Contact) {
        _deleteContact.value = contact
    }

    fun onAddContact() {
        _navigateToContact.value = ContactNavigateEvent.AddContactNavigate
    }


    fun onDeleteContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.delete(contact)
        }
    }

    fun onSearchContact(query: String) {
        contactDao.searchContact(query).observeForever { list -> _contacts.value = list }
    }

    fun onNavigationCompleted() {
        _navigateToContact.value = null
    }

    sealed class ContactNavigateEvent() {
        data object AddContactNavigate : ContactNavigateEvent()
        data class EditContactNavigate(val contactId: Long) : ContactNavigateEvent()
    }
}