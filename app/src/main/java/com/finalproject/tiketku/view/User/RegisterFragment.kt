package com.finalproject.tiketku.view.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.databinding.FragmentRegisterBinding
import com.finalproject.tiketku.viewmodel.UsersViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        // Register button click listener
        binding.btnRegister.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val nomor_telepon = binding.noEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            usersViewModel.postDataUsers(username, email, nomor_telepon, password)
        }

        // Observe the postDataUsers LiveData
        usersViewModel.postUsers().observe(viewLifecycleOwner, { responseUsersItem ->
            if (responseUsersItem != null) {
                // Registration successful
                Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()

                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(action)
            } else {
                // Registration failed
                Toast.makeText(requireContext(), "Registration failed!", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}
