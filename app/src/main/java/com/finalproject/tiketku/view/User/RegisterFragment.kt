package com.finalproject.tiketku.view.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentRegisterBinding
import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.viewmodel.UsersViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var userVM: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userVM = ViewModelProvider(this).get(UsersViewModel::class.java)

        binding.btnAdaAkun.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val username = binding.usernameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val nomor_telepon = binding.noEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (username.isEmpty() || email.isEmpty() || nomor_telepon.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Registration Failed: Input fields cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (username.length < 5 || password.length < 5) {
            Toast.makeText(requireContext(), "Registration Failed: Username and password must be at least 5 characters", Toast.LENGTH_SHORT).show()
            return
        }

        val dataPostUser = userVM.dataPostUserRegis
        dataPostUser.observe(viewLifecycleOwner, { response ->

            if (response != null) { Toast.makeText(requireContext(), "Registration Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

            } else { Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_SHORT).show()

            }
        })

        userVM.postUserRegister(
            DataPostUsersItem(
                username,
                email,
                nomor_telepon,
                password
            )
        )
    }
}
