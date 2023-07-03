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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentVerifikasiBinding
import com.finalproject.tiketku.model.DataPassword
import com.finalproject.tiketku.model.DataResetPassword
import com.finalproject.tiketku.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint

class VerifikasiFragment : Fragment() {
    private lateinit var binding: FragmentVerifikasiBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var resetPasswordViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        // Initialize ViewModel
        resetPasswordViewModel = ViewModelProvider(this)[UsersViewModel::class.java]

        binding.tvMintaKode.setOnClickListener {
            val email = sharedPreferences.getString("email", "")
            resetPasswordViewModel.dataLoginPassword.observe(viewLifecycleOwner) { dataLoginPassword ->
                if (dataLoginPassword != null) {

                    Toast.makeText(requireContext(), "OTP Telah dikirim Email", Toast.LENGTH_SHORT).show()

                    Log.d("LoginFragment", "email resetPassword = $email")
                } else {
                    // Login failed
                    Toasty.error(requireContext(), "Reset Password Failed", Toast.LENGTH_SHORT, true)
                        .show()
                }
            }
            resetPasswordViewModel.postUserPassword(DataPassword(email))
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_verifikasiFragment_to_resetPasswordFragment)
        }
        binding.btnSubmit.setOnClickListener {
            val token = binding.tokenEditText.text.toString()
            val password = sharedPreferences.getString("password", "")

            Log.d("VerifikasiFragment", "Password  = $password")
            Log.d("VerifikasiFragment", "Token  = $token")

            if (token.isEmpty() || password.isNullOrEmpty()) {
                Toasty.warning(requireContext(), "Token and password are required", Toast.LENGTH_SHORT, true).show()
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
                        Toasty.error(requireContext(), "Reset Password Failed!!", Toast.LENGTH_SHORT, true).show()
                    }
                }

                // Post reset password request
                resetPasswordViewModel.postUserResetPassword(dataResetPassword)
            }
        }
    }

}