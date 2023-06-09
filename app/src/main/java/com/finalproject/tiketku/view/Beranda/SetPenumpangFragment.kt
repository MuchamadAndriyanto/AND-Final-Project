package com.finalproject.tiketku.view.Beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentSetPenumpangBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SetPenumpangFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSetPenumpangBinding
    private var baby = 0
    private var child = 0
    private var adult = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetPenumpangBinding.inflate(inflater, container, false)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        //button tambah kurang Baby
        binding.tvPassangerBaby.text = baby.toString()
        binding.addPassangerBaby.setOnClickListener {
            babyTambah()
        }
        binding.decPassangerBaby.setOnClickListener {
            babyKurang()
        }

        //button tambah kurang Child
        binding.tvPassangerChild.text = child.toString()
        binding.addPassangerChild.setOnClickListener {
            childTambah()
        }
        binding.decPassangerChild.setOnClickListener {
            childKurang()
        }

        //button tambah kurang Adult
        binding.tvPassangerAdult.text = adult.toString()
        binding.addPassangerAdult.setOnClickListener {
            adultTambah()
        }
        binding.decPassangerAdult.setOnClickListener {
            adultKurang()
        }
    }


    private fun babyTambah(){
        baby++
        binding.tvPassangerBaby.text = baby.toString()
    }

    private fun childTambah(){
        child++
        binding.tvPassangerChild.text = child.toString()
    }

    private fun adultTambah(){
        adult++
        binding.tvPassangerAdult.text = adult.toString()
    }

    private fun babyKurang() {
        if (baby > 0) {
            baby--
            binding.tvPassangerBaby.text = baby.toString()
        }
    }

    private fun childKurang() {
        if (child > 0) {
            child--
            binding.tvPassangerChild.text = child.toString()
        }
    }

    private fun adultKurang() {
        if (adult > 0) {
            adult--
            binding.tvPassangerAdult.text = adult.toString()
        }
    }
}