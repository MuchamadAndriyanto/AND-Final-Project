package com.finalproject.tiketku.view.DetailPenerbangan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentHasilPencarianBinding
import com.finalproject.tiketku.databinding.FragmentNoResultDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoResultDetailFragment : Fragment() {

    lateinit var binding: FragmentNoResultDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoResultDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ivclose.setOnClickListener {
            findNavController().navigate(R.id.action_noResultDetailFragment_to_homeFragment)
        }

        binding.btnUbahPencarian.setOnClickListener {
            findNavController().navigate(R.id.action_noResultDetailFragment_to_homeFragment)
        }
    }
}