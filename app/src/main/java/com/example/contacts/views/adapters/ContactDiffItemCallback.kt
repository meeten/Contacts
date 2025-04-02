package com.example.contacts.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.contacts.models.Contact

class ContactDiffItemCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}