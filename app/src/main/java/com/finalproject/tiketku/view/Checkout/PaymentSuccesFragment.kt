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
import com.finalproject.tiketku.databinding.FragmentPaymentBinding
import com.finalproject.tiketku.databinding.FragmentPaymentSuccesBinding
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSuccesFragment : Fragment() {

    private lateinit var binding: FragmentPaymentSuccesBinding
    private lateinit var detailViewModel: RiwayatViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentSuccesBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_ORDER_ID = "order_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getInt(ARG_ORDER_ID, 0) ?: 0
        Log.d("PaymentSuccessFragment", "orderId: $orderId")

        binding.btnLogin.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(RincianOrderFragment.ARG_ORDER_ID, orderId)
            }
            findNavController().navigate(R.id.action_paymentSuccesFragment_to_rincianOrderFragment, bundle)
        }

        binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_paymentSuccesFragment_to_paymentFragment)
        }
    }

}