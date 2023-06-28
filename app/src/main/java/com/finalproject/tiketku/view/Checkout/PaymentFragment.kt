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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailBinding
import com.finalproject.tiketku.databinding.FragmentPaymentBinding
import com.finalproject.tiketku.model.payment.Data
import com.finalproject.tiketku.model.payment.DataPost
import com.finalproject.tiketku.viewmodel.PaymentViewModel
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var token: String
    private lateinit var sharedPref: SharedPreferences
    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_ORDER_ID = "order_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderId = arguments?.getInt(ARG_ORDER_ID, 0) ?: 0
        Log.d("PaymentFragment", "orderId $orderId")

        val departureCountry = arguments?.getString("departureCountry", "")
        val destinationAirport = arguments?.getString("destinationAirport", "")
        val jamBerangkat = arguments?.getString("jamBerangkat")
        val jamKedatangan = arguments?.getString("jamKedatangan")
        val tanggalBerangkat = arguments?.getString("tanggalBerangkat")
        val tanggalKedatangan = arguments?.getString("tanggalKedatangan")
        val jumlahPenumpang = arguments?.getString("jumlahPenumpang")
        val totalHargaTiket = arguments?.getString("totalHargaTiket")
        val selisihJam = arguments?.getString("selisihJam")
        val selisihMenit = arguments?.getString("selisihMenit")

        binding.tvjakarta.text = departureCountry
        binding.tvMelbourne.text = destinationAirport
        binding.tvBerangkat.text = tanggalBerangkat
        binding.tvKedatangan.text = tanggalKedatangan
        binding.tvJamBerangkat.text = jamBerangkat
        binding.tvJamKedatangan.text = jamKedatangan
        binding.tvAdulst.text = " $jumlahPenumpang Adults"
        binding.tvTotalHarga.text = totalHargaTiket
        binding.tvJam.text = selisihJam
        binding.tvMenit.text = selisihMenit

        // Inisialisasi ViewModel
        paymentViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        binding.btnbayar.setOnClickListener {
            val cardNumber = binding.etNoCard.text.toString()
            val cardName = binding.etName.text.toString()
            val cvv = binding.etCvv.text.toString()
            val expiryDate = binding.etExpdate.text.toString()

            if (cardNumber.isNotEmpty() && cardName.isNotEmpty() && cvv.isNotEmpty() && expiryDate.isNotEmpty()) {
                // Mengatur nilai orderId dari argument yang diterima
                val paymentData = DataPost(orderId, cardNumber, cardName, cvv, expiryDate)

                paymentViewModel.postPayment(token, paymentData)
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi data pembayaran", Toast.LENGTH_SHORT).show()
            }
        }

        paymentViewModel.getPaymentSuccessfulLiveData().observe(viewLifecycleOwner, { isPaymentSuccessful ->
            if (isPaymentSuccessful) {
                Log.d("PaymentFragment", "Pembayaran sukses")
                findNavController().navigate(R.id.action_paymentFragment_to_paymentSuccesFragment)
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
