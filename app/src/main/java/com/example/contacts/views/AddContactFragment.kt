package com.example.contacts.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.contacts.R
import com.example.contacts.databinding.FragmentContactControlBinding
import com.example.contacts.models.ContactDatabase
import com.example.contacts.views.controls.ContactControlViewModel
import com.example.contacts.views.controls.ContactControlViewModelFactory

class AddContactFragment : Fragment() {
    private var _binding: FragmentContactControlBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactControlViewModelFactory: ContactControlViewModelFactory
    private lateinit var contactControlViewModel: ContactControlViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactControlBinding.inflate(inflater)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val contactDao = ContactDatabase.getInstance(application).contactDao
        contactControlViewModelFactory = ContactControlViewModelFactory(contactDao = contactDao)
        contactControlViewModel = ViewModelProvider(
            this,
            contactControlViewModelFactory
        )[ContactControlViewModel::class.java]
        binding.contactControlViewModel = contactControlViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        contactControlViewModel.navigateToContacts.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_addContactFragment_to_contactsFragment)
                contactControlViewModel.onNavigationCompleted()
            }
        }

        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}