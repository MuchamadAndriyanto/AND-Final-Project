package com.finalproject.tiketku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.NotifAdapter
import com.finalproject.tiketku.databinding.FragmentNotificationsBinding
import com.finalproject.tiketku.model.notif.DataNotif
import com.finalproject.tiketku.viewmodel.NotifViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelHasilPencarian = ViewModelProvider(this).get(NotifViewModel::class.java)

        viewModelHasilPencarian.getNotif()
        viewModelHasilPencarian.livedataNotifikasi.observe(viewLifecycleOwner, Observer { favList ->
            if (favList != null) {
                val adapter = NotifAdapter(requireContext(), favList)
                binding.rvNotif.layoutManager = LinearLayoutManager(requireContext())
                binding.rvNotif.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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