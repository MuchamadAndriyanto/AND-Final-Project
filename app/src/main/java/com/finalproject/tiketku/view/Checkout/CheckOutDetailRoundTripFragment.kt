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
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailRoundTripBinding
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutDetailRoundTripFragment : Fragment() {
    private lateinit var binding: FragmentCheckOutDetailRoundTripBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var rincianViewModel: OrderViewModel
    private var orderIdRT: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutDetailRoundTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        rincianViewModel = ViewModelProvider(requireActivity()).get(OrderViewModel::class.java)
        rincianViewModel.setOrderId(orderIdRT)

        // Mengambil nilai orderId dari argument jika tersedia
        orderIdRT = arguments?.getInt("orderIdRT", 0) ?: orderIdRT

        val token = sharedPref.getString("accessToken", "")

        // Memanggil fungsi getOrders untuk mendapatkan detail pesanan
        if (!token.isNullOrEmpty() && orderIdRT != 0) {
            Log.d("Rincian", "Order Token: $token")
            Log.d("Rincian", "Order Id: $orderIdRT")
            rincianViewModel.getOrdersRT(token, orderIdRT)
        } else {
            // Token tidak tersedia atau orderId tidak valid, lakukan sesuatu
            Toast.makeText(requireContext(), "Gagal mendapatkan pesanan", Toast.LENGTH_SHORT).show()
        }

        rincianViewModel.liveDataGetRTs.observe(viewLifecycleOwner, { detail ->
            if (detail != null) {
                binding.tvDeparture.text = detail.tiket.berangkat.idBandaraAsal
                binding.tvDestination.text = detail.tiket.berangkat.idBandaraTujuan
                binding.detailTime.text = detail.tiket.berangkat.jamBerangkat
//                binding.tvSelisihJam.setText("(" + detail.tiket.berangkat.jamBerangkat + "h - ");
//                binding.tvSelisihMenit.setText(detail.tiket.berangkat.selisihMenit.toString() + "m)");
                binding.detailDate.text = detail.tiket.berangkat.tanggalBerangkat
                binding.detailAirport.text = detail.tiket.berangkat.bandaraAwal.namaBandara
                binding.Maskapai.text = detail.tiket.berangkat.maskapai.namaMaskapai
                binding.kodeMaskapai.text = detail.tiket.berangkat.maskapai.kodeMaskapai
                binding.detailTimeArrived.text = detail.tiket.berangkat.jamKedatangan
                binding.detailDateArrived.text = detail.tiket.berangkat.tanggalKedatangan
                binding.detailAirportArrived.text = detail.tiket.berangkat.bandaraTujuan.namaBandara

                binding.tvDeparturePulang.text = detail.tiket.pulang.idBandaraAsal
                binding.tvDestinationPulang.text = detail.tiket.pulang.idBandaraTujuan
                binding.detailTimePulang.text = detail.tiket.pulang.jamBerangkat
//                binding.tvSelisihJam.setText("(" + detail.tiket.berangkat.jamBerangkat + "h - ");
//                binding.tvSelisihMenit.setText(detail.tiket.berangkat.selisihMenit.toString() + "m)");
                binding.detailDatePulang.text = detail.tiket.pulang.tanggalBerangkat
                binding.detailAirportPulang.text = detail.tiket.pulang.bandaraAwal.namaBandara
                binding.MaskapaiPulang.text = detail.tiket.pulang.maskapai.namaMaskapai
                binding.kodeMaskapaiPulang.text = detail.tiket.pulang.maskapai.kodeMaskapai
                binding.detailTimeArrivedPulang.text = detail.tiket.pulang.jamKedatangan
                binding.detailDateArrivedPulang.text = detail.tiket.pulang.tanggalKedatangan
                binding.detailAirportArrivedPulang.text = detail.tiket.pulang.bandaraTujuan.namaBandara


                val sharedPreferences = requireContext().getSharedPreferences("passenger_counts", Context.MODE_PRIVATE)
                // Mendapatkan nilai default untuk adultCount
                val defaultAdultCount = 1

                // Mendapatkan nilai adultCount dari Shared Preferences
                val adultCount = sharedPreferences.getInt("totalPassengers", defaultAdultCount)

                binding.tvAdult.text = "$adultCount"

                binding.tvHargaAdult.text = detail.totalHargaTiket
                binding.detailTotalHarga.text = detail.totalHargaTiket

                binding.btnSubmit.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt(PaymentRountripFragment.ARG_ORDER_ID, orderIdRT)
                    }
                    Log.d("payment", "Order ID: $orderIdRT")
                    Log.d("Payment", "Bundle: $bundle")
                    findNavController().navigate(R.id.action_checkOutDetailRoundTripFragment_to_paymentRountripFragment, bundle)
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
