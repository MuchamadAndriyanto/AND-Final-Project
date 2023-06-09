package com.finalproject.tiketku.view.Beranda

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val switchMaterial = binding.switchMaterial

        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            val thumbTint = if (isChecked) {
                ContextCompat.getColor(requireContext(), R.color.purple)  // Purple color
            } else {
                ContextCompat.getColor(requireContext(), R.color.white)  // Grey color
            }

            switchMaterial.thumbTintList = ColorStateList.valueOf(thumbTint)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Passangers.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_setPenumpangFragment)
        }

        binding.SeatClass.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_setClassFragment)
        }

        binding.flDeparture.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_destinasiPencarianFragment)
        }
        binding.flTujuan.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_destinasiPencarianFragment)
        }
        binding.btnPencarian.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_hasilPencarianFragment)
        }

    }
}