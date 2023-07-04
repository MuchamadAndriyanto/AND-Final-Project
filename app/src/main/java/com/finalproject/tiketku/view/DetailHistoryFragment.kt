package com.finalproject.tiketku.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentDetailHistoryBinding
import com.finalproject.tiketku.view.Checkout.PaymentFragment
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("RemoveRedundantCallsOfConversionMethods", "RemoveRedundantCallsOfConversionMethods",
    "RemoveRedundantCallsOfConversionMethods", "RemoveRedundantCallsOfConversionMethods",
    "RemoveRedundantCallsOfConversionMethods", "RemoveRedundantCallsOfConversionMethods"
)
@AndroidEntryPoint
class DetailHistoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailHistoryBinding
    private lateinit var detailViewModel: RiwayatViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        token = sharedPref.getString("accessToken", "") ?: ""

        detailViewModel = ViewModelProvider(requireActivity())[RiwayatViewModel::class.java]

        // Ambil ID order dari arguments
        val args = DetailHistoryFragmentArgs.fromBundle(requireArguments())
        orderId = args.id

        detailViewModel.getRiwayat(token, orderId)

        detailViewModel.liveDataDetailOrder.observe(viewLifecycleOwner) { detail ->
            if (detail != null && detail.isNotEmpty()) {
                val data = detail.find { it.order.id == orderId }
                if (data != null) {
                    binding.tvBolean.text = data.order.status_pembayaran
                    binding.tvCode.text = data.order.kode_booking
                    binding.detailTime.text = data.tiketBerangkat.jam_berangkat
                    binding.detailDate.text = data.tiketBerangkat.tanggal_berangkat
                    binding.detailAirport.text = data.tiketBerangkat.bandaraAwal.nama_bandara
                    binding.Maskapai.text = data.penerbanganBerangkat.maskapai.nama_maskapai

                    binding.detailTimeArrived.text = data.tiketBerangkat.jam_kedatangan
                    binding.detailDateArrived.text = data.tiketBerangkat.tanggal_kedatangan
                    binding.detailAirportArrived.text = data.tiketBerangkat.bandaraTujuan.nama_bandara
                    binding.kodeMaskapai.text = data.penerbanganBerangkat.maskapai.id_maskapai.toString()
                    binding.detailPemesan.text = data.order.nama_lengkap
                    binding.tvAdult.text = "${data.order.jumlah_penumpang} Adulst"
                    binding.tvHargaAdult.text = data.totalHargaTiketBerangkat
                    binding.detailTotalHarga.text = data.totalHargaTiketBerangkat

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
                findNavController().navigate(R.id.action_detailHistoryFragment_to_homeFragment, null, navOptions)
            } else {
                navController.navigate(R.id.action_detailHistoryFragment_to_paymentFragment, bundle)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}