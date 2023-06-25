package com.finalproject.tiketku.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.NotifAdapter
import com.finalproject.tiketku.adapter.RiwayatAdapter
import com.finalproject.tiketku.databinding.FragmentHistoryBinding
import com.finalproject.tiketku.databinding.FragmentNotificationsBinding
import com.finalproject.tiketku.viewmodel.NotifViewModel
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import com.finalproject.tiketku.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var viewModel: RiwayatViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        viewModel = ViewModelProvider(this).get(RiwayatViewModel::class.java)

        viewModel.getRiwayat(token)
        viewModel.liveRiwayat.observe(viewLifecycleOwner, { responseData ->
            if (responseData != null) {
                val riwayatList = listOf(responseData)
                val adapter = RiwayatAdapter(requireContext(), riwayatList)
                binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
                binding.rvRiwayat.adapter = adapter
            }
        })
    }
}
