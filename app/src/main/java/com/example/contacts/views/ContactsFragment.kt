package com.example.contacts.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contacts.R
import com.example.contacts.databinding.ContactItemBinding
import com.example.contacts.databinding.FragmentContactControlBinding
import com.example.contacts.databinding.FragmentContactsBinding
import com.example.contacts.models.Contact
import com.example.contacts.models.ContactDatabase
import com.example.contacts.views.adapters.ContactItemAdapter
import com.example.contacts.views.viewmodel.ContactsViewModel
import com.example.contacts.views.viewmodel.ContactsViewModelFactory

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactsViewModelFactory: ContactsViewModelFactory
    private lateinit var contactsViewModel: ContactsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val contactDao = ContactDatabase.getInstance(application).contactDao
        contactsViewModelFactory = ContactsViewModelFactory(contactDao)
        contactsViewModel =
            ViewModelProvider(this, contactsViewModelFactory)[ContactsViewModel::class.java]
        binding.contactViewModel = contactsViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter =
            ContactItemAdapter(clickListener = { contactId ->
                contactsViewModel.onClickContact(
                    contactId
                )
            },
                clickLongListener = { contact ->
                    contactsViewModel.onClickLongContact(contact)
                    showToolbar()
                    true
                })
        binding.rcView.adapter = adapter

        contactsViewModel.contacts.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        contactsViewModel.navigateToContact.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ContactsViewModel.ContactNavigateEvent.AddContactNavigate -> {
                    this.findNavController()
                        .navigate(R.id.action_contactsFragment_to_addContactFragment)
                    contactsViewModel.onNavigationCompleted()
                }

                is ContactsViewModel.ContactNavigateEvent.EditContactNavigate -> {
                    val action =
                        ContactsFragmentDirections.actionContactsFragmentToEditContactFragment(event.contactId)
                    this.findNavController().navigate(action)
                    contactsViewModel.onNavigationCompleted()
                }

                else -> return@observe
            }
        }


        contactsViewModel.deleteContact.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                binding.deleteButton.setOnClickListener {
                    contactsViewModel.onDeleteContact(contact)
                    hideToolbar()
                }
            }
        }

        return binding.root
    }


    private fun showToolbar() {
        binding.toolbar.visibility = View.VISIBLE
        binding.appName.visibility = View.GONE

        binding.cancelDeleteButton.setOnClickListener {
            hideToolbar()
        }
    }

    private fun hideToolbar() {
        binding.toolbar.visibility = View.GONE
        binding.appName.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}