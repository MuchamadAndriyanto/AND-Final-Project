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
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var selectedStartDate: String? = null
    private var selectedReturnDate: String? = null
    private var selectedDate: String? = null
    private var babyCount = 0
    private var childCount = 0
    private var adultCount = 0
    private var totalPassengerCount = 0
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferenceListener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
        updatePassengerCount()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        sharedPreferences = requireContext().getSharedPreferences("passenger_counts", Context.MODE_PRIVATE)


        val switchMaterial = binding.switchMaterial
        val tvPilihTanggal = binding.tvPilihTanggal
        val dateReturn = binding.dateReturn

        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            val thumbTint = if (isChecked) {
                ContextCompat.getColor(requireContext(), R.color.purple)  // Purple color
            } else {
                ContextCompat.getColor(requireContext(), R.color.white)  // Grey color
            }
            val textColor = if (isChecked) {
                ContextCompat.getColor(requireContext(), R.color.purple)  // Warna ungu
            } else {
                ContextCompat.getColor(requireContext(), R.color.grey)  // Warna abu-abu
            }
            tvPilihTanggal.setTextColor(textColor)
            // Mengatur status enabled pada "Pilih tanggal" TextView berdasarkan status switch
            tvPilihTanggal.isEnabled = isChecked

            switchMaterial.thumbTintList = ColorStateList.valueOf(thumbTint)
            tvPilihTanggal.isFocusable = isChecked
            dateReturn.isClickable = isChecked


            if (!isChecked) {
                // Reset nilai tanggal kembali saat switch dinonaktifkan
                selectedReturnDate = ""
                tvPilihTanggal.text = "Pilih tanggal"
            }

            binding.dateDeparture.setOnClickListener {
                showDatePicker(requireContext()) { date ->
                    selectedStartDate = date
                    binding.tvDateDeparture.text = date
                }
            }

            binding.dateReturn.setOnClickListener {
                if (switchMaterial.isChecked) {
                    showDatePicker(requireContext()) { date ->
                        selectedReturnDate = date
                        binding.tvPilihTanggal.text = date
                    }
                }
            }

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

        binding.dateDeparture.setOnClickListener {
            showDatePicker(requireContext()) { date ->
                selectedDate = date
                binding.tvDateDeparture.text = date
            }
        }

        binding.tvPenumpang.text = "1" // Inisialisasi nilai awal total penumpang

        // Listen for the result from SetClassFragment
        parentFragmentManager.setFragmentResultListener("selected_class", this) { _, result ->
            val className = result.getString("selected_class", "")
            binding.tvBusiness.text = className
        }
    }

    private fun showDatePicker(
        context: Context,
        onDateSelected: (date: String) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                val date = "$dayOfMonth ${getMonthName(monthOfYear)} $year"
                onDateSelected(date)
            },
            initialYear,
            initialMonth,
            initialDay
        )

        datePickerDialog.show()
    }

    private fun getMonthName(month: Int): String {
        val dateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return dateFormat.format(calendar.time)
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceListener)
        updatePassengerCount()

    }

    override fun onPause() {
        super.onPause()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceListener)
    }

    private fun updatePassengerCount() {
        babyCount = sharedPreferences.getInt("baby", 0)
        childCount = sharedPreferences.getInt("child", 0)
        adultCount = sharedPreferences.getInt("adult", 0)
        val totalPassengers = babyCount + childCount + adultCount
        totalPassengerCount = totalPassengers
        binding.tvPenumpang.text = totalPassengers.toString()
    }

}
