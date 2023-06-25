package com.finalproject.tiketku.view.Checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailBinding
import com.finalproject.tiketku.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {

    private lateinit var binding : FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentFragment_to_checkOutDetailFragment)
        }
    }

}