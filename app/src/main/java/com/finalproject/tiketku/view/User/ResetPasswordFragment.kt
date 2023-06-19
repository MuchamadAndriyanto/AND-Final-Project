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
import com.finalproject.tiketku.databinding.FragmentResetPasswordBinding
import com.finalproject.tiketku.model.DataLoginUserItem
import com.finalproject.tiketku.model.DataPassword
import com.finalproject.tiketku.model.DataResetPassword
import com.finalproject.tiketku.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        binding.ivBack.setOnClickListener{
            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
        }
        binding.btnSimpan.setOnClickListener {
            val password = binding.passwordEdiText.text.toString()
            val confirmPassword = binding.Confirmpassword.text.toString()

            if (password == confirmPassword) {
                // Simpan password ke Shared Preferences
                val editor = sharedPreferences.edit()
                editor.putString("password", password)
                editor.apply()
                Log.d("ResetPassword", "Passwordnya = $password")
                Log.d("ResetPassword", "Passwordnya = $editor")

                // Navigasi ke halaman VerifikasiFragment
                findNavController().navigate(R.id.action_resetPasswordFragment_to_verifikasiFragment)
            } else {
                Toast.makeText(requireContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
//
//        loginUserVM = ViewModelProvider(this).get(UsersViewModel::class.java)
//
//        binding.ivBack.setOnClickListener{
//            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
//        }
//        binding.btnSimpan.setOnClickListener {
//            val passwordBaru = binding.inputPassword.editText?.text.toString()
//            val konfirmasiPassword = binding.inputConfirmPassword.editText?.text.toString()
//
//            if (passwordBaru.isEmpty() || konfirmasiPassword.isEmpty()) {
//                Toast.makeText(requireContext(), "Masukkan Password dan Konfirmasi Password", Toast.LENGTH_SHORT).show()
//            } else if (passwordBaru != konfirmasiPassword) {
//                Toast.makeText(requireContext(), "Password dan Konfirmasi Password tidak cocok", Toast.LENGTH_SHORT).show()
//            } else {
//                loginUserVM.dataResetPassword.observe(viewLifecycleOwner) { dataResetPassword ->
//                    if (dataResetPassword != null) {
//
//                        Toast.makeText(requireContext(), "Password berhasil diubah", Toast.LENGTH_SHORT).show();                        val currentDestination = findNavController().currentDestination
//                        findNavController().popBackStack()
//                        // Kirim data password baru ke endpoint
//                    }else// Login failed
//                        Toast.makeText(requireContext(), "Reset Password Failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            loginUserVM.postUserResetPassword(DataResetPassword(passwordBaru,konfirmasiPassword))
//        }
//    }

