package com.finalproject.tiketku.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private lateinit var riwayatAdapter: RiwayatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RiwayatViewModel::class.java)
        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        riwayatAdapter = RiwayatAdapter(requireContext(), emptyList())
        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRiwayat.adapter = riwayatAdapter

        viewModel.getRiwayat(token)

        viewModel.liveData.observe(viewLifecycleOwner, { responseData ->
            responseData?.let {
                riwayatAdapter.list = it
                riwayatAdapter.notifyDataSetChanged()
                Log.d("HistoryFragment", "Data riwayat berhasil diatur dalam adapter")
            }
        })
    }
}
