package com.finalproject.tiketku.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.adapter.NotifAdapter
import com.finalproject.tiketku.databinding.FragmentNotificationsBinding
import com.finalproject.tiketku.model.notif.DataNotif
import com.finalproject.tiketku.viewmodel.NotifViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentNotificationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        val viewModelHasilPencarian = ViewModelProvider(this).get(NotifViewModel::class.java)

        val token = sharedPref.getString("accessToken", "") ?: ""

        viewModelHasilPencarian.getNotif(token)
        viewModelHasilPencarian.livedataNotifikasi.observe(viewLifecycleOwner, { favList ->
            if (favList != null && favList.isNotEmpty()) {

                Log.d("Notif", "token: $token")
                        val adapter = NotifAdapter(requireContext(), favList)
                        binding.rvNotif.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvNotif.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.rvNotif.adapter = adapter

                        adapter.setOnItemClickListener(object : NotifAdapter.OnItemClickListener {
                            override fun onItemClick(item: DataNotif) {
                                showNotificationDialog(item)
                            }
                        })
                    }

        })
    }

    private fun showNotificationDialog(item: DataNotif) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(item.judul)
            .setMessage(item.pesan)
            .setPositiveButton("OK") { dialog, _ ->
                // Tindakan saat tombol OK ditekan
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

}