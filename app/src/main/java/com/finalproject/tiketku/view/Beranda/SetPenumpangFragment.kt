package com.finalproject.tiketku.view.Beranda

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentSetPenumpangBinding
import com.finalproject.tiketku.model.PassengerCountListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPenumpangFragment : BottomSheetDialogFragment() {

    // Deklarasikan listener
    private var passengerCountListener: PassengerCountListener? = null
    private lateinit var binding: FragmentSetPenumpangBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var baby = 0
    private var child = 0
    private var adult = 0
    private var totalPassengerCount = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetPenumpangBinding.inflate(inflater, container, false)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        return binding.root



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("baby", Context.MODE_PRIVATE)
        sharedPreferences = requireContext().getSharedPreferences("child", Context.MODE_PRIVATE)
        sharedPreferences = requireContext().getSharedPreferences("adult", Context.MODE_PRIVATE)
        sharedPreferences = requireContext().getSharedPreferences("passenger_counts", Context.MODE_PRIVATE)

        // Retrieve the saved counter value
        baby = sharedPreferences.getInt("baby", 0)
        child = sharedPreferences.getInt("child", 0)
        adult = sharedPreferences.getInt("adult", 0)
        totalPassengerCount = sharedPreferences.getInt("passenger_counts", 0)

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

        // Set listener untuk mengirim data penumpang saat tombol "Simpan" diklik
        binding.btnSimpan.setOnClickListener {
            passengerCountListener?.onPassengerCountUpdated(baby, child, adult)
            // Save the counter values to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putInt("baby", baby)
            editor.putInt("child", child)
            editor.putInt("adult", adult)
            editor.putInt("passenger_counts", totalPassengerCount)
            editor.apply()


            // Logging the saved counter values
            Log.d("SetPenumpangFragment", "Saved baby count: $baby")
            Log.d("SetPenumpangFragment", "Saved child count: $child")
            Log.d("SetPenumpangFragment", "Saved adult count: $adult")
            Log.d("SetPenumpangFragment", "Total passenger count: $totalPassengerCount")

            dismiss()
        }
        updateTotalPassengerCount()
    }
    private fun updateTotalPassengerCount() {
        totalPassengerCount = baby + child + adult
        // Update the UI to display the total passenger count
        // For example: binding.tvTotalPassengerCount.text = totalPassengerCount.toString()
    }

    private fun babyTambah() {
        baby++
        binding.tvPassangerBaby.text = baby.toString()
        sharedPreferences.edit().putInt("baby", baby).apply()
        updateTotalPassengerCount()
    }

    private fun childTambah() {
        child++
        binding.tvPassangerChild.text = child.toString()
        sharedPreferences.edit().putInt("child", child).apply()
        updateTotalPassengerCount()
    }

    private fun adultTambah() {
        adult++
        binding.tvPassangerAdult.text = adult.toString()
        sharedPreferences.edit().putInt("adult", adult).apply()
        updateTotalPassengerCount()
    }

    private fun babyKurang() {
        if (baby > 0) {
            baby--
            binding.tvPassangerBaby.text = baby.toString()
            sharedPreferences.edit().putInt("baby", baby).apply()
            updateTotalPassengerCount()
        }
    }

    private fun childKurang() {
        if (child > 0) {
            child--
            binding.tvPassangerChild.text = child.toString()
            sharedPreferences.edit().putInt("child", child).apply()
            updateTotalPassengerCount()
        }
    }

    private fun adultKurang() {
        if (adult > 0) {
            adult--
            binding.tvPassangerAdult.text = adult.toString()
            sharedPreferences.edit().putInt("adult", adult).apply()
            updateTotalPassengerCount()
        }
    }
}