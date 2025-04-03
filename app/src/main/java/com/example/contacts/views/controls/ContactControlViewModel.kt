package com.example.contacts.views.controls

import android.util.Patterns
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
    companion object {
        private const val NAME_REQUIRED = "Поле Name не должно быть пустым"
        private const val EMAIL_REQUIRED = "Поле Email не должно быть пустым"
        private const val EMAIL_INVALID = "Неверный формат поля Email"
    }


    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact> get() = _contact

    private val _navigateToContacts = MutableLiveData<Boolean>()
    val navigateToContacts: LiveData<Boolean> get() = _navigateToContacts

    private val _errorName = MutableLiveData<String?>()
    val errorName: MutableLiveData<String?> get() = _errorName

    private val _errorEmail = MutableLiveData<String?>()
    val errorEmail: MutableLiveData<String?> get() = _errorEmail

    init {
        viewModelScope.launch {
            _contact.value = if (contactId == null) Contact() else contactDao.get(contactId)
        }
    }

    fun onSaveContact() {
        val correctContact = _contact.value ?: return
        _errorName.value = null
        _errorEmail.value = null

        var hasErrors = false
        if (correctContact.contactName.isEmpty()) {
            _errorName.value = NAME_REQUIRED
            hasErrors = true
        }

        if (correctContact.contactEmail.isEmpty()) {
            _errorEmail.value = EMAIL_REQUIRED
            hasErrors = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correctContact.contactEmail).matches()) {
            _errorEmail.value = EMAIL_INVALID
            hasErrors = true
        }

        if (!hasErrors) {
            viewModelScope.launch {
                if (contactId == null) {
                    contactDao.insert(correctContact)
                } else {
                    contactDao.update(correctContact)
                }

                _navigateToContacts.value = true
            }
        }


    }

    fun onClickBackButton() {
        _navigateToContacts.value = true
    }

    fun onNavigationCompleted() {
        _navigateToContacts.value = false
    }
}