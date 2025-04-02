package com.example.contacts.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.models.ContactDao

class ContactsViewModelFactory(private val contactDao: ContactDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java))
            return ContactsViewModel(contactDao) as T
        throw IllegalArgumentException("Такой модели представления не существует")
    }
}