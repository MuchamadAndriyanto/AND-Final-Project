package com.finalproject.tiketku.view.User

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentHomeBinding
import com.finalproject.tiketku.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var username: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
//        username = sharedPref.getString("email", "") ?: ""
//
//        // Gunakan variabel username untuk mengakses dan menampilkan informasi pengguna di tampilan profil
//        val tvUsername = view.findViewById<TextView>(R.id.tvAkun)
//        tvUsername.text = username

        val tvKeluar = view.findViewById<TextView>(R.id.tvKeluar)
        tvKeluar.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.tvProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_akunFragment)
        }
    }

    private fun showLogoutConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("Apakah anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                logout()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun logout() {
        // Hapus Shared Preferences
        sharedPref.edit().clear().apply()

        val navController = findNavController()
        navController.navigate(R.id.action_profileFragment_to_splashFragment)
    }
}