package com.finalproject.tiketku.view.Checkout

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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.RiwayatAdapter
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailBinding
import com.finalproject.tiketku.databinding.FragmentRincianOrderBinding
import com.finalproject.tiketku.viewmodel.OrderViewModel
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RincianOrderFragment : Fragment() {

    private lateinit var binding: FragmentRincianOrderBinding
    private lateinit var detailViewModel: RiwayatViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRincianOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_ORDER_ID = "order_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getInt(ARG_ORDER_ID, 0) ?: 0
        Log.d("RincianOrderFragment", "orderId: $orderId")

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        token = sharedPref.getString("accessToken", "") ?: ""

        detailViewModel = ViewModelProvider(requireActivity()).get(RiwayatViewModel::class.java)

        detailViewModel.getRiwayat(token, orderId)

        detailViewModel.liveDataDetailOrder.observe(viewLifecycleOwner) { detail ->
            if (detail != null && detail.isNotEmpty()) {
                val data = detail.find { it.order.id == orderId }
                if (data != null) {
                    binding.tvBolean.text = data.order.status_pembayaran
                    binding.tvCode.text = data.order.kode_booking
                    binding.detailTime.text = data.tiket.jam_berangkat
                    binding.detailDate.text = data.tiket.tanggal_berangkat
                    binding.detailAirport.text = data.tiket.bandaraAwal.nama_bandara
                    binding.Maskapai.text = data.tiket.maskapai.nama_maskapai

                    binding.detailTimeArrived.text = data.tiket.jam_kedatangan
                    binding.detailDateArrived.text = data.tiket.tanggal_kedatangan
                    binding.detailAirportArrived.text = data.tiket.bandaraTujuan.nama_bandara
                    binding.kodeMaskapai.text = data.tiket.maskapai.id_maskapai.toString()

                    binding.tvAdult.text = "${data.order.jumlah_penumpang.toString()} Adulst"
                    binding.detailTotalHarga.text = data.totalHargaTiket

                }
            }
        }

        binding.btnSubmit.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.historyFragment, true)
                .build()
            findNavController().navigate(R.id.action_rincianOrderFragment_to_homeFragment, null, navOptions)
        }


    }
}