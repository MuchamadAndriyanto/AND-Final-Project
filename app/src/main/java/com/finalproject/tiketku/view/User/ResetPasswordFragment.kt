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
import com.finalproject.tiketku.databinding.FragmentResetPasswordBinding
import com.finalproject.tiketku.model.DataLoginUserItem
import com.finalproject.tiketku.model.DataPassword
import com.finalproject.tiketku.model.DataResetPassword
import com.finalproject.tiketku.viewmodel.UsersViewModel

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var loginUserVM: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginUserVM = ViewModelProvider(this).get(UsersViewModel::class.java)

        binding.btnSimpan.setOnClickListener {
            val passwordBaru = binding.inputPassword.editText?.text.toString()
            val konfirmasiPassword = binding.inputConfirmPassword.editText?.text.toString()

            if (passwordBaru.isEmpty() || konfirmasiPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Masukkan Password dan Konfirmasi Password", Toast.LENGTH_SHORT).show()
            } else if (passwordBaru != konfirmasiPassword) {
                Toast.makeText(requireContext(), "Password dan Konfirmasi Password tidak cocok", Toast.LENGTH_SHORT).show()
            } else {
                // Kirim data password baru ke endpoint
                loginUserVM.postUserResetPassword(DataResetPassword(passwordBaru, konfirmasiPassword))

                Toast.makeText(requireContext(), "Password berhasil diubah", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
            }
        }
    }

}