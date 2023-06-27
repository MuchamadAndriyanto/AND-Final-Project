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
        Log.d("PaymentFragment", "Order ID: $orderId")

        // Inisialisasi ViewModel
        paymentViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        binding.btnbayar.setOnClickListener {
            // Validasi input pembayaran (misalnya, cek apakah semua data terisi dengan benar)

            val cardNumber = binding.etNoCard.text.toString()
            val cardName = binding.etName.text.toString()
            val cvv = binding.etCvv.text.toString()
            val expiryDate = binding.etExpdate.text.toString()

            if (cardNumber.isNotEmpty() && cardName.isNotEmpty() && cvv.isNotEmpty() && expiryDate.isNotEmpty()) {
                val paymentData = Data(cardName, cardNumber, cvv, expiryDate, 0, orderId, 0)

                // Lakukan proses pembayaran
                paymentViewModel.postPayment(token, paymentData)
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi data pembayaran", Toast.LENGTH_SHORT).show()
            }
        }

        paymentViewModel.postPaymentIdLiveData().observe(viewLifecycleOwner, { paymentId ->
            if (paymentId != null) {
                // Proses pembayaran berhasil
                Log.d("PaymentFragment", "Pembayaran sukses, paymentId: $paymentId")
                findNavController().navigate(R.id.action_paymentFragment_to_paymentSuccesFragment)
            } else {
                // Proses pembayaran gagal
                Log.e("PaymentFragment", "Gagal melakukan pembayaran")
                Toast.makeText(requireContext(), "Gagal melakukan pembayaran", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
