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
import com.finalproject.tiketku.databinding.FragmentLoginBinding
import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.network.ApiClient
import com.finalproject.tiketku.viewmodel.UsersViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    /*private val apiService = ApiClient.create()*/
    private lateinit var userVM: UsersViewModel
    lateinit var sharepref: SharedPreferences

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

        sharepref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tvLupaSandi.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

/*        binding.btnLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEdiText.text.toString()

            // Panggil metode login
            login(email, password)
        }*/

               binding.btnLogin.setOnClickListener {
                   if (binding.emailEditText.text.toString().isEmpty()) {
                       binding.passwordEdiText.setError("Isi Username")
                   } else if (binding.passwordEdiText.text.toString().isEmpty()) {
                       binding.passwordEdiText.setError("Isi Password")
                   } else {
                       forLogin()
                   }
               }
    }

    /*private fun login(email: String, password: String) {

        val call = apiService.getLogin(dataUsers)
        call.enqueue(object : Callback<List<ResponseUsersItem>> {
            override fun onResponse(call: Call<List<ResponseUsersItem>>, response: Response<List<ResponseUsersItem>>) {
                if (response.isSuccessful) {

                    val userList = response.body()
                    if (userList != null) {
                        // Cek apakah pengguna dengan username yang diberikan ada dalam daftar pengguna
                        val matchingUser = userList.find { it.email == email }
                        val userId = matchingUser?.id.toString()
                        Log.d("LoginFragment", "User ID: $userId")

                        if (matchingUser != null && matchingUser.password == password) {
                            // Login berhasil
                            Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            // Simpan ID pengguna ke SharedPreferences
                            val sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("userId", matchingUser?.id.toString())
                            editor.apply()
                            val buttonClicked = getButtonClicked()

                            // Navigasi ke halaman favorit jika tombol favorit ditekan sebelumnya
                            *//*if (buttonClicked == "favorite") {
                                // Panggil API untuk mendapatkan daftar favorit pengguna
                              *//**//*  favoriteViewModel.callApiFavorite(userId)
                                findNavController().navigate(R.id.action_loginFragment_to_favoriteFragment)
                            *//**//*}
                            // Navigasi ke halaman profil jika tombol profil ditekan sebelumnya
                            else if (buttonClicked == "profile") {
                            *//**//*    findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                           *//**//* }
                            // Navigasi ke halaman beranda jika tidak ada tombol yang ditekan sebelumnya
                            else {
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            }*//*
                        }
                    } else {
                        // Login gagal
                        Toast.makeText(context, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<List<ResponseUsersItem>>, t: Throwable) {
                // Kegagalan koneksi atau request
                Toast.makeText(context, "Terjadi kesalahan saat melakukan login", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getButtonClicked(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        return sharedPreferences.getString("buttonClicked", null)
    }*/


        lateinit var listuserlogin: List<DataPostUsersItem>
        private fun forLogin() {
            userVM = ViewModelProvider(this).get(UsersViewModel::class.java)
            userVM.dataLoginUser.observe(viewLifecycleOwner, Observer {
                listuserlogin = it
                loginAuth(listuserlogin)
            })
            userVM.UserLogin()
        }

        private fun loginAuth(userDataList: List<DataPostUsersItem>) {
            //make shared preference that saving log in activity history
            sharepref = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)

            //get all data from user input
            val inputEmail = binding.emailEditText.text.toString()
            val inputPassword = binding.passwordEdiText.text.toString()

            //checking email and password of user to authenticate
            for (i in userDataList.indices) {
                if (inputPassword == userDataList[i].password && inputEmail == userDataList[i].email) {
                    Toast.makeText(requireContext(), "Berhasil login", Toast.LENGTH_SHORT).show()
                    //bundling all information about user
                    navigationBundlingSf(userDataList[i])
                    break
                } else if (i == userDataList.lastIndex && inputPassword != userDataList[i].password && inputEmail != userDataList[i].email) {
                    binding.emailEditText.error = "Password Tidak Sesuai"
                    binding.passwordEdiText.error = "Email Tidak Sesuai"
                }
            }
        }

        private fun navigationBundlingSf(currentUser: DataPostUsersItem) {
            sharepref = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
            //shared pref to save log in history
            val sharedPref = sharepref.edit()
            sharedPref.putString("email", currentUser.email)
            sharedPref.putString("password", currentUser.password)
            sharedPref.apply()
        }

}