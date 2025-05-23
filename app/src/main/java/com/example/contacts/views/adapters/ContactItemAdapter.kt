package com.example.contacts.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.ContactItemBinding
import com.example.contacts.models.Contact

class ContactItemAdapter(
    private val clickListener: (contactId: Long) -> Unit,
    private val clickLongListener: (contact: Contact) -> Boolean
) :
    ListAdapter<Contact, ContactItemAdapter.ContactViewHolder>(ContactDiffItemCallback()) {
    class ContactViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Contact,
            clickListener: (contactId: Long) -> Unit,
            clickLongListener: (contact: Contact) -> Boolean
        ) {
            binding.item = item
            binding.root.setOnClickListener { clickListener(item.contactId) }
            binding.root.setOnLongClickListener { clickLongListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, clickLongListener)
    }
}