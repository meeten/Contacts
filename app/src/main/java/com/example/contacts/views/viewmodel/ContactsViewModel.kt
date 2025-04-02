package com.example.contacts.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.models.Contact
import com.example.contacts.models.ContactDao

class ContactsViewModel(private val contactDao: ContactDao) : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    private val _navigateToContact = MutableLiveData<ContactNavigateEvent?>()
    val navigateToContact: MutableLiveData<ContactNavigateEvent?> get() = _navigateToContact

    init {
        loadContacts()
    }

    private fun loadContacts() {
        contactDao.getAll().observeForever { list -> _contacts.value = list }
    }

    fun onClickContact(contactId: Long) {
        _navigateToContact.value = ContactNavigateEvent.EditContactNavigate(contactId)
    }

    fun onAddContact() {
        _navigateToContact.value = ContactNavigateEvent.AddContactNavigate
    }

    fun onNavigationCompleted() {
        _navigateToContact.value = null
    }

    sealed class ContactNavigateEvent() {
        data object AddContactNavigate : ContactNavigateEvent()
        data class EditContactNavigate(val contactId: Long) : ContactNavigateEvent()
    }
}