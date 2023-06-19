package com.finalproject.tiketku.view.User

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentVerifikasiBinding
import com.finalproject.tiketku.model.DataResetPassword
import com.finalproject.tiketku.viewmodel.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class VerifikasiFragment : Fragment() {
    private lateinit var binding: FragmentVerifikasiBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var resetPasswordViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        // Initialize ViewModel
        resetPasswordViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_verifikasiFragment_to_resetPasswordFragment)
        }
        binding.btnSubmit.setOnClickListener {
            val token = binding.tokenEditText.text.toString()
            val password = sharedPreferences.getString("password", "")

            Log.d("VerifikasiFragment", "Password  = $password")
            Log.d("VerifikasiFragment", "Token  = $token")

            if (token.isEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Token and password are required", Toast.LENGTH_SHORT).show()
            } else {
                val dataResetPassword = DataResetPassword(token, password)

                // Observe reset password response
                resetPasswordViewModel.dataResetPassword.observe(viewLifecycleOwner) { response ->
                    if (response != null) {
                        Toast.makeText(requireContext(), "Reset Success", Toast.LENGTH_SHORT).show()
                        Log.d("VerifikasiFragment", "Password Reset = $password")
                        Log.d("VerifikasiFragment", "Token Reset = $token")

                            findNavController().navigate(R.id.action_verifikasiFragment_to_loginFragment)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Reset Password Failed!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // Post reset password request
                resetPasswordViewModel.postUserResetPassword(dataResetPassword)
            }
        }
    }

}