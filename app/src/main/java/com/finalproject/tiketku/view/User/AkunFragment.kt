package com.finalproject.tiketku.view.User

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.finalproject.tiketku.databinding.FragmentAkunBinding
import com.finalproject.tiketku.model.profile.UpdateProfilePost
import com.finalproject.tiketku.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AkunFragment : Fragment() {
    private lateinit var binding: FragmentAkunBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        val username = sharedPref.getString("username", "")
        val namaLengkap = sharedPref.getString("namaLengkap", "")
        val alamat = sharedPref.getString("alamat", "")
        val nomorTelepon = sharedPref.getString("nomorTelepon", "")

        binding.editTextUsername.setText(username)
        binding.editTextNamaLengkap.setText(namaLengkap)
        binding.editTextAlamat.setText(alamat)
        binding.noEditText.setText(nomorTelepon)

        binding.btnUpdate.setOnClickListener {

            val newUsername = binding.editTextUsername.text.toString()
            val newNamaLengkap = binding.editTextNamaLengkap.text.toString()
            val newAlamat = binding.editTextAlamat.text.toString()
            val newNomorTelepon = binding.noEditText.text.toString()

            // Simpan data pengguna yang diperbarui ke SharedPreferences
            val editor = sharedPref.edit()
            editor.putString("username", newUsername)
            editor.putString("namaLengkap", newNamaLengkap)
            editor.putString("alamat", newAlamat)
            editor.putString("nomorTelepon", newNomorTelepon)
            editor.apply()

            // Buat objek UpdateProfilePost dengan data yang diperbarui
            val updateProfile = UpdateProfilePost(newUsername, newNamaLengkap, newAlamat, newNomorTelepon)

            // Panggil fungsi updateProfile dari ViewModel
            viewModel.updateProfile(token, updateProfile)

            // Tampilkan pesan sukses
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
        }
    }
}


