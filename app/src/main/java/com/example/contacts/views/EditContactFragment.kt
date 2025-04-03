package com.example.contacts.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.contacts.R
import com.example.contacts.databinding.FragmentContactControlBinding
import com.example.contacts.models.ContactDatabase
import com.example.contacts.views.controls.ContactControlViewModel
import com.example.contacts.views.controls.ContactControlViewModelFactory

class EditContactFragment : Fragment() {
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
        val contactId = EditContactFragmentArgs.fromBundle(requireArguments()).contactId
        contactControlViewModelFactory = ContactControlViewModelFactory(contactId, contactDao)
        contactControlViewModel = ViewModelProvider(
            this,
            contactControlViewModelFactory
        )[ContactControlViewModel::class.java]
        binding.contactControlViewModel = contactControlViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        contactControlViewModel.navigateToContacts.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_editContactFragment_to_contactsFragment)
                contactControlViewModel.onNavigationCompleted()
            }
        }

        settingToolbar()

        return view
    }

    private fun settingToolbar() {
        val appCompatActivity = (activity as AppCompatActivity)
        appCompatActivity.setSupportActionBar(binding.toolbar)
        val navHostFragment =
            appCompatActivity.supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val configuration = builder.build()
        binding.toolbar.setupWithNavController(navController, configuration)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}