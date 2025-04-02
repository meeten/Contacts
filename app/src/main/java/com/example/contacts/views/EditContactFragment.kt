package com.example.contacts.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contacts.databinding.FragmentContactControlBinding

class EditContactFragment : Fragment() {
    private var _binding: FragmentContactControlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactControlBinding.inflate(inflater)
        val view = binding.root

        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}