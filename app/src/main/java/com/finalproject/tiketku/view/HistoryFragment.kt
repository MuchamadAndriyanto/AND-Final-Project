package com.finalproject.tiketku.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.RiwayatAdapter
import com.finalproject.tiketku.databinding.FragmentHistoryBinding
import com.finalproject.tiketku.model.riwayat.Data
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var viewModel: RiwayatViewModel
    private lateinit var token: String
    private lateinit var riwayatAdapter: RiwayatAdapter
    private var riwayatPesananKosongFragment: RiwayatPesananKosongFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Extension property untuk mendapatkan data dari List<Data>
    val List<Data>.data: List<Data>
        get() = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RiwayatViewModel::class.java)
        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        riwayatAdapter = RiwayatAdapter(requireContext(), emptyList())
        binding.rvRiwayat.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRiwayat.adapter = riwayatAdapter

        viewModel.liveData.observe(viewLifecycleOwner, Observer { riwayatData ->
            if (riwayatData.data.isNotEmpty()) {
                hideRiwayatPesananKosongFragment()
                riwayatAdapter.list = riwayatData.data
                riwayatAdapter.notifyDataSetChanged()
                Log.d("HistoryFragment", "Data riwayat berhasil diatur dalam adapter")
            } else {
                showRiwayatPesananKosongFragment()
            }
        })

        viewModel.getRiwayat(token, null)
    }


    // Fungsi untuk menampilkan fragment RiwayatPesananKosong
    private fun showRiwayatPesananKosongFragment() {
        if (riwayatPesananKosongFragment == null) {
            riwayatPesananKosongFragment = RiwayatPesananKosongFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, riwayatPesananKosongFragment!!)
                .commit()
        }
    }

    // Fungsi untuk menyembunyikan fragment RiwayatPesananKosong
    private fun hideRiwayatPesananKosongFragment() {
        if (riwayatPesananKosongFragment != null) {
            parentFragmentManager.beginTransaction()
                .remove(riwayatPesananKosongFragment!!)
                .commit()
            riwayatPesananKosongFragment = null
        }
    }
}
