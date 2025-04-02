package com.example.contacts.views.controls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.models.ContactDao

class ContactControlViewModelFactory(
    private val contactId: Long? = null,
    private val contactDao: ContactDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactControlViewModel::class.java))
            return ContactControlViewModel(contactId, contactDao) as T
        throw IllegalArgumentException("Такой модели представления не существует")
    }
}