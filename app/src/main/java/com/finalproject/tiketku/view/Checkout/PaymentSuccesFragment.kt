package com.finalproject.tiketku.view.Checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentPaymentBinding
import com.finalproject.tiketku.databinding.FragmentPaymentSuccesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSuccesFragment : Fragment() {

    private lateinit var binding:FragmentPaymentSuccesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentSuccesBinding.inflate(inflater, container, false)
        return binding.root
    }

}