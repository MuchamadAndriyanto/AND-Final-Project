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
import com.finalproject.tiketku.databinding.FragmentPaymentRountripBinding
import com.finalproject.tiketku.databinding.FragmentPaymentSuccesRountripBinding
import com.finalproject.tiketku.model.payment.DataPost
import com.finalproject.tiketku.viewmodel.PaymentViewModel
import com.finalproject.tiketku.viewmodel.RiwayatRtViewModel
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentRountripFragment : Fragment() {

    private lateinit var binding: FragmentPaymentRountripBinding
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var detailViewModel: RiwayatRtViewModel
    private lateinit var token: String
    private lateinit var sharedPref: SharedPreferences
    private var orderIdRT: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentRountripBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_ORDER_ID = "order_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil nilai orderId dari argument jika tersedia
        orderIdRT = arguments?.getInt(PaymentRountripFragment.ARG_ORDER_ID, 0) ?: 0
        Log.d("PaymentRountTripFragment", "Order ID: $orderIdRT")

        // Inisialisasi ViewModel
        paymentViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        detailViewModel = ViewModelProvider(requireActivity()).get(RiwayatRtViewModel::class.java)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        detailViewModel.getRiwayatRt(token, orderIdRT)

        detailViewModel.liveDataDetailOrder.observe(viewLifecycleOwner) { detail ->
            if (detail != null && detail.isNotEmpty()) {
                val data = detail.find { it.order.id == orderIdRT }
                if (data != null) {
                    // Tiket Berangkat
                    binding.tvAdulst.text = " ${data.order.jumlah_penumpang.toString()} Passenger"
                    binding.tvjakarta.text = data.tiketBerangkat.bandaraAwal.kota
                    binding.tvBerangkat.text = data.tiketBerangkat.tanggal_berangkat
                    binding.tvJamBerangkat.text = data.tiketBerangkat.jam_berangkat

                    binding.tvJam.setText("(" + data.tiketPulang.selisih_jam + "h - ")
                    binding.tvMenit.setText(data.tiketBerangkat.selisih_menit.toString() + "m)")

                    binding.tvMelbourne.text = data.tiketBerangkat.bandaraTujuan.kota
                    binding.tvKedatangan.text = data.tiketBerangkat.tanggal_kedatangan
                    binding.tvJamKedatangan.text = data.tiketBerangkat.jam_kedatangan

                    binding.tvBooking.text = data.order.kode_booking
                    binding.tvTotalHarga.text = data.totalHargaTiketBerangkat

                    // Tiket Pulang
                    binding.tvAdulst2.text = " ${data.order.jumlah_penumpang.toString()} Passenger"
                    binding.tvjakarta2.text = data.tiketPulang.bandaraAwal.kota
                    binding.tvBerangkat2.text = data.tiketPulang.tanggal_berangkat
                    binding.tvJamBerangkat2.text = data.tiketPulang.jam_berangkat

                    binding.tvJam2.setText("(" + data.tiketPulang.selisih_jam + "h - ")
                    binding.tvMenit2.setText(data.tiketPulang.selisih_menit.toString() + "m)")

                    binding.tvMelbourne2.text = data.tiketPulang.bandaraTujuan.kota
                    binding.tvKedatangan2.text = data.tiketPulang.tanggal_kedatangan
                    binding.tvJamKedatangan2.text = data.tiketPulang.jam_kedatangan

                    binding.tvBooking2.text = data.order.kode_booking
                    binding.tvTotalHarga2.text = data.totalHargaTiketPulang
                }
            }
        }

        binding.btnbayar.setOnClickListener {
            val cardNumber = binding.etNoCard.text.toString()
            val cardName = binding.etName.text.toString()
            val cvv = binding.etCvv.text.toString()
            val expiryDate = binding.etExpdate.text.toString()

            if (cardNumber.isNotEmpty() && cardName.isNotEmpty() && cvv.isNotEmpty() && expiryDate.isNotEmpty()) {
                // Mengatur nilai orderId dari argument yang diterima
                val paymentData = DataPost(orderIdRT, cardNumber, cardName, cvv, expiryDate)

                paymentViewModel.postPayment(token, paymentData)
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi data pembayaran", Toast.LENGTH_SHORT).show()
            }
        }

        paymentViewModel.getPaymentSuccessfulLiveData().observe(viewLifecycleOwner, { isPaymentSuccessful ->
            if (isPaymentSuccessful) {
                val bundle = Bundle().apply {
                    putInt(PaymentSuccesRountripFragment.ARG_ORDER_ID, orderIdRT)
                }
                Log.d("PaymentFragment", "Pembayaran sukses")
                findNavController().navigate(R.id.action_paymentRountripFragment_to_paymentSuccesRountripFragment, bundle)
            } else {
                Log.e("PaymentFragment", "Gagal melakukan pembayaran")
                Toast.makeText(requireContext(), "Gagal melakukan pembayaran", Toast.LENGTH_SHORT).show()
            }
        })

        paymentViewModel.getPaymentLiveData().observe(viewLifecycleOwner, { payment ->
            if (payment == null) {
                Log.e("PaymentFragment", "Gagal memperoleh data pembayaran")
                Toast.makeText(requireContext(), "Gagal memperoleh data pembayaran", Toast.LENGTH_SHORT).show()
            }
        })


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentFragment_to_checkOutDetailFragment)
        }
    }
}
