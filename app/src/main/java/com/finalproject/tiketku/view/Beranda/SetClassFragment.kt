package com.finalproject.tiketku.view.Beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentSetClassBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SetClassFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSetClassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentSetClassBinding.inflate(inflater, container, false)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

    }

}