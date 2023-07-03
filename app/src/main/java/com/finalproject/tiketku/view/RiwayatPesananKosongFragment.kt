package com.finalproject.tiketku.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.tiketku.databinding.FragmentRiwayatPesananKosongBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@Suppress("RedundantOverride")
@AndroidEntryPoint

class RiwayatPesananKosongFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRiwayatPesananKosongBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRiwayatPesananKosongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
