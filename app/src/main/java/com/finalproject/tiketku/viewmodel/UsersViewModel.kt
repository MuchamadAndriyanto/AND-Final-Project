package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.DataUsers
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.network.ApiClient
import com.finalproject.tiketku.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {
    private val _response = MutableLiveData<ResponseUsersItem>()
    val response: LiveData<ResponseUsersItem> get() = _response

    fun postDataUsers(user: DataUsers) {
        // Mengirim permintaan POST menggunakan ApiService
        val apiService = ApiClient.instance
        val call: Call<ResponseUsersItem> = apiService.postDataUsers(user)
        call.enqueue(object : Callback<ResponseUsersItem> {
            override fun onResponse(
                call: Call<ResponseUsersItem>,
                response: Response<ResponseUsersItem>
            ) {
                if (response.isSuccessful) {
                    _response.value = response.body()
                } else {
                    // Penanganan kesalahan jika permintaan tidak berhasil
                    // Misalnya, dapat menyimpan pesan kesalahan ke dalam variabel LiveData terpisah
                }
            }

            override fun onFailure(call: Call<ResponseUsersItem>, t: Throwable) {
                // Penanganan kesalahan saat permintaan gagal
            }
        })
    }
}