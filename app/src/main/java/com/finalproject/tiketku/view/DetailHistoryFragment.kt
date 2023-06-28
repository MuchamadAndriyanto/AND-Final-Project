package com.finalproject.tiketku.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.RiwayatAdapter
import com.finalproject.tiketku.databinding.FragmentDetailHistoryBinding
import com.finalproject.tiketku.databinding.FragmentDetailPenerbanganBinding
import com.finalproject.tiketku.view.DetailPenerbangan.DetailPenerbanganFragmentArgs
import com.finalproject.tiketku.view.DetailPenerbangan.DetailPenerbanganFragmentDirections
import com.finalproject.tiketku.viewmodel.DetailViewModel
import com.finalproject.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHistoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailPenerbanganFragment_to_homeFragment)
        }

    }
}
