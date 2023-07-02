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
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailBinding
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutDetailFragment : Fragment() {
    private lateinit var binding: FragmentCheckOutDetailBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var detailViewModel: OrderViewModel
    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        detailViewModel = ViewModelProvider(requireActivity()).get(OrderViewModel::class.java)
        detailViewModel.setOrderId(orderId)

        // Mengambil nilai orderId dari argument jika tersedia
        orderId = arguments?.getInt("orderId", 0) ?: orderId

        val token = sharedPref.getString("accessToken", "")

        // Memanggil fungsi getOrders untuk mendapatkan detail pesanan
        if (!token.isNullOrEmpty() && orderId != 0) {
            detailViewModel.getOrders(token, orderId)
        } else {
            // Token tidak tersedia atau orderId tidak valid, lakukan sesuatu
            Toast.makeText(requireContext(), "Gagal mendapatkan pesanan", Toast.LENGTH_SHORT).show()
        }

        detailViewModel.liveDetailOrders.observe(viewLifecycleOwner, { detail ->
            if (detail != null) {
                binding.tvDeparture.text = detail.tiket.idBandaraAsal
                binding.tvDestination.text = detail.tiket.idBandaraTujuan
                binding.detailTime.text = detail.tiket.jamBerangkat
                binding.tvSelisihJam.setText("(" + detail.tiket.selisihJam + "h - ");
                binding.tvSelisihMenit.setText(detail.tiket.selisihMenit.toString() + "m)");
                binding.detailDate.text = detail.tiket.tanggalBerangkat
                binding.detailAirport.text = detail.tiket.bandaraAwal.namaBandara
                binding.Maskapai.text = detail.tiket.maskapai.namaMaskapai
                binding.kodeMaskapai.text = detail.tiket.maskapai.kode_maskapai
                binding.detailTimeArrived.text = detail.tiket.jamKedatangan
                binding.detailDateArrived.text = detail.tiket.tanggalKedatangan
                binding.detailAirportArrived.text = detail.tiket.bandaraTujuan.namaBandara

                val sharedPreferences = requireContext().getSharedPreferences("passenger_counts", Context.MODE_PRIVATE)
                // Mendapatkan nilai default untuk adultCount
                val defaultAdultCount = 1

                // Mendapatkan nilai adultCount dari Shared Preferences
                val adultCount = sharedPreferences.getInt("totalPassengers", defaultAdultCount)

                binding.tvAdult.text = "$adultCount "

                binding.tvHargaAdult.text = detail.tiket.maskapai.hargaTiket
                binding.detailTotalHarga.text = detail.totalHargaTiket

                binding.btnSubmit.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt(PaymentFragment.ARG_ORDER_ID, orderId)
                    }
                    Log.d("Payment", "Order ID: $orderId")
                    Log.d("Payment", "Bundle: $bundle")
                    findNavController().navigate(R.id.action_checkOutDetailFragment_to_paymentFragment, bundle)
                }
            } else {
                // Permintaan GET tidak berhasil, lakukan sesuatu
                Toast.makeText(requireContext(), "Gagal menampilkan pesanan", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}
