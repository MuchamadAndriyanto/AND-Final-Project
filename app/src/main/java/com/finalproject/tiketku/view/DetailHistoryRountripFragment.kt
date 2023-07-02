package com.finalproject.tiketku.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentDetailHistoryBinding
import com.finalproject.tiketku.databinding.FragmentDetailHistoryRountripBinding
import com.finalproject.tiketku.view.Checkout.PaymentFragment
import com.finalproject.tiketku.viewmodel.RiwayatRtViewModel
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHistoryRountripFragment : Fragment() {

    private lateinit var binding: FragmentDetailHistoryRountripBinding
    private lateinit var detailViewModel: RiwayatRtViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHistoryRountripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        token = sharedPref.getString("accessToken", "") ?: ""

        detailViewModel = ViewModelProvider(requireActivity()).get(RiwayatRtViewModel::class.java)

        // Ambil ID order dari arguments
        val args = DetailHistoryRountripFragmentArgs.fromBundle(requireArguments())
        orderId = args.id

        detailViewModel.getRiwayatRt(token, orderId)

        detailViewModel.liveDataDetailOrder.observe(viewLifecycleOwner) { detail ->
            if (detail != null && detail.isNotEmpty()) {
                val data = detail.find { it.order.id == orderId }
                if (data != null) {
                    // Tiket Berangkat
                    binding.tvBolean.text = data.order.status_pembayaran
                    binding.tvCode.text = data.order.kode_booking
                    binding.detailTime.text = "${data.tiketBerangkat.jam_berangkat} WIB"
                    binding.detailDate.text = data.tiketBerangkat.tanggal_berangkat
                    binding.detailAirport.text = data.tiketBerangkat.bandaraAwal.nama_bandara
                    binding.Maskapai.text = data.penerbanganBerangkat.maskapai.nama_maskapai
                    binding.kodeMaskapai.text = data.penerbanganBerangkat.maskapai.id_maskapai.toString()

                    binding.detailTimeArrived.text = "${data.tiketBerangkat.jam_kedatangan} WIB"
                    binding.detailDateArrived.text = data.tiketBerangkat.tanggal_kedatangan
                    binding.detailAirportArrived.text = data.tiketBerangkat.bandaraTujuan.nama_bandara

                    // Tiket Pulang
                    binding.tvCodee.text = data.order.kode_booking
                    binding.detailTimePulang.text = "${data.tiketPulang.jam_berangkat} WIB"
                    binding.detailDatePulang.text = data.tiketPulang.tanggal_berangkat
                    binding.detailAirportPulang.text = data.tiketPulang.bandaraAwal.nama_bandara
                    binding.MaskapaiPulang.text = data.penerbanganPulang.maskapai.nama_maskapai
                    binding.kodeMaskapaiPulang.text = data.penerbanganPulang.maskapai.id_maskapai.toString()

                    binding.detailTimeArrivedPulang.text = "${data.tiketPulang.jam_kedatangan} WIB"
                    binding.detailDateArrivedPulang.text = data.tiketPulang.tanggal_kedatangan
                    binding.detailAirportArrivedPulang.text = data.tiketPulang.bandaraTujuan.nama_bandara

                    binding.tvAdult.text = data.order.jumlah_penumpang.toString()
                    binding.tvHargaAdult.text = data.formatTiketKeseluruhan
                    binding.detailTotalHarga.text = data.formatTiketKeseluruhan


                    binding.btnSubmit.text = if (binding.tvBolean.text == "paid") {
                        "Kembali ke Beranda"
                    } else {
                        "Lanjutkan Pembayaran"
                    }

                }
            }
        }

        binding.btnSubmit.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(PaymentFragment.ARG_ORDER_ID, orderId)
            }

            val navController = findNavController()

            if (binding.tvBolean.text == "paid") {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.historyFragment, true)
                    .build()
                findNavController().navigate(R.id.action_detailHistoryRountripFragment_to_homeFragment, null, navOptions)
            } else {
                navController.navigate(R.id.action_detailHistoryRountripFragment_to_paymentRountripFragment, bundle)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}