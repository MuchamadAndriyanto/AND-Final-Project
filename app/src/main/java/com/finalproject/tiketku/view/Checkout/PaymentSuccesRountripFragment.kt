package com.finalproject.tiketku.view.Checkout

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentPaymentSuccesRountripBinding
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSuccesRountripFragment : Fragment() {
    private lateinit var binding: FragmentPaymentSuccesRountripBinding
    private lateinit var detailViewModel: RiwayatViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private var orderIdRT: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentSuccesRountripBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_ORDER_ID = "order_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil nilai orderId dari argument jika tersedia
        orderIdRT = arguments?.getInt(ARG_ORDER_ID, 0) ?: 0
        Log.d("RountTripFragment", "Order ID: $orderIdRT")

        binding.btnLogin.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(RincianOrderRountripFragment.ARG_ORDER_ID, orderIdRT)
            }
            findNavController().navigate(R.id.action_paymentSuccesRountripFragment_to_rincianOrderRountripFragment, bundle)
        }

        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}