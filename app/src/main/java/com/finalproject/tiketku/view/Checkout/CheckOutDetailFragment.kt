package com.finalproject.tiketku.view.Checkout

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailBinding
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutDetailFragment : Fragment() {

    private lateinit var binding: FragmentCheckOutDetailBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var detailViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        detailViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val token = sharedPref.getString("accessToken", "")

        detailViewModel.getOrderIdLiveData().observe(viewLifecycleOwner, { orderId ->
            if (!token.isNullOrEmpty() && orderId != null) {
                val orderIdValue = detailViewModel.getOrderId()
                Log.d("idOrder", "id di rincian nya : $orderIdValue, dan $orderId")
                detailViewModel.getOrders(token, orderIdValue)
            }
        })


        detailViewModel.liveDetailOrders.observe(viewLifecycleOwner, { detail ->
            if (detail != null) {
                binding.tvDeparture.text = detail.tiket.idBandaraAsal
                binding.tvDestination.text = detail.tiket.idBandaraTujuan
                binding.detailTime.text = detail.tiket.tanggalBerangkat
                binding.detailAirport.text = detail.tiket.bandaraAwal.namaBandara
            }
        })

        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutDetailFragment_to_paymentFragment2)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutDetail_to_selectSeatFragment)
        }

        val orderId = detailViewModel.getOrderId()
        if (orderId != null) {
            detailViewModel.setOrderId(orderId)
        }
    }
}
