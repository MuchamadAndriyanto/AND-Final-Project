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
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.RiwayatAdapter
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailBinding
import com.finalproject.tiketku.databinding.FragmentRincianOrderBinding
import com.finalproject.tiketku.viewmodel.OrderViewModel
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RincianOrderFragment : Fragment() {

    private lateinit var binding: FragmentRincianOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRincianOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_rincianOrderFragment_to_homeFragment)
        }

    }
}