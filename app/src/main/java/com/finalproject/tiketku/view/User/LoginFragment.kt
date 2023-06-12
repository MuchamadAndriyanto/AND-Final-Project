package com.finalproject.tiketku.view.User

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentLoginBinding
import com.finalproject.tiketku.model.DataLoginUserItem
import com.finalproject.tiketku.model.DataPassword
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.viewmodel.UsersViewModel

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var loginUserVM: UsersViewModel
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginUserVM = ViewModelProvider(this).get(UsersViewModel::class.java)
        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tvLupaSandi.setOnClickListener {
           lupaSandi()
        }



        binding.btnLogin.setOnClickListener {
            login()
        }

    }

    private fun lupaSandi() {
        val email = binding.emailEditText.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Masukkan Email", Toast.LENGTH_SHORT).show()

        } else {
            loginUserVM.postUserPassword(DataPassword(email))
//          Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
//          Simpan status login ke shared preferences
//            val editor = sharedPref.edit()
//            editor.putBoolean("isLoggedIn", true)
//            editor.apply()
//
//            // Simpan email dan password pengguna ke SharedPreferences
//            editor.putString("email", email)
//            editor.putString("password", password)
//            editor.apply()
//
//// Ambil ID halaman terakhir yang diklik dari shared preferences
//            val lastClickedItemId = sharedPref.getInt("clickedMenuItemId", R.id.homeFragment)
//
//// Navigasikan pengguna ke halaman terakhir yang diklik
//            findNavController().navigate(lastClickedItemId)
//
//        }
//    }
        }
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEdiText.text.toString()

        if (email.isEmpty()|| password.isEmpty()) {
            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()

        } else {
            loginUserVM.postUserLogin(DataLoginUserItem( email, password))
            Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
// Simpan status login ke shared preferences
            val editor = sharedPref.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            // Simpan email dan password pengguna ke SharedPreferences
            editor.putString("email", email)
            editor.putString("password", password)
            editor.apply()

// Ambil ID halaman terakhir yang diklik dari shared preferences
            val lastClickedItemId = sharedPref.getInt("clickedMenuItemId", R.id.homeFragment)

// Navigasikan pengguna ke halaman terakhir yang diklik
            findNavController().navigate(lastClickedItemId)

        }
    }

}