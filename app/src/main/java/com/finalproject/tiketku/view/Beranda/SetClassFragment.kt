package com.finalproject.tiketku.view.Beranda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentSetClassBinding
import com.finalproject.tiketku.databinding.FragmentSetPenumpangBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SetClassFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSetClassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }

}