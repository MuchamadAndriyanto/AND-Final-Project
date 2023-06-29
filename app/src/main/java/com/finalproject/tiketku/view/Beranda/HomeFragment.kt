package com.finalproject.tiketku.view.Beranda

import com.finalproject.tiketku.viewmodel.FavoritDestinasiViewModel
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
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.adapter.DestinasiFavoritAdapter
import com.finalproject.tiketku.model.favorit.DataFavorite
import com.finalproject.tiketku.view.HasilPencarianFragment
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var selectedStartDate: String? = ""
    private var selectedReturnDate: String? = null
    private var selectedDate: String? = null
    private var loadingDialog: Dialog? = null
    private var babyCount = 0
    private var childCount = 0
    private var adultCount = 0
    private var totalPassengerCount = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dateSharedPreferences: SharedPreferences
    private val sharedPreferenceListener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
        updatePassengerCount()
    }
    private fun convertDateFormat(date: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        val parsedDate = inputFormat.parse(date)
        return outputFormat.format(parsedDate)
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
                ContextCompat.getColor(requireContext(), R.color.purple)
            } else {
                ContextCompat.getColor(requireContext(), R.color.white)
            }
            val textColor = if (isChecked) {
                ContextCompat.getColor(requireContext(), R.color.purple)
            } else {
                ContextCompat.getColor(requireContext(), R.color.grey)
            }
            tvPilihTanggal.setTextColor(textColor)
            // Mengatur status enabled pada "Pilih tanggal" TextView berdasarkan status switch
            tvPilihTanggal.isEnabled = isChecked

            switchMaterial.thumbTintList = ColorStateList.valueOf(thumbTint)
            tvPilihTanggal.isFocusable = isChecked
            dateReturn.isClickable = isChecked

            if (!isChecked) {
                // Reset nilai tanggal kembali saat switch dinonaktifkan
                val dateSharedPrefrens = requireContext().getSharedPreferences("date", Context.MODE_PRIVATE)
                selectedReturnDate = ""
                tvPilihTanggal.text = "Pilih tanggal"
                val editor = dateSharedPrefrens.edit()
                editor.remove("returnDate")
                editor.apply()

                Log.d(
                    "test", "Update $selectedReturnDate"
                )
            }
            val dateSharedPrefrens = requireContext().getSharedPreferences("date", Context.MODE_PRIVATE)
            // Set onClickListener untuk dateDeparture
            binding.dateDeparture.setOnClickListener {
                showDatePicker(requireContext()) { date ->
                    selectedStartDate = date
                    binding.tvDateDeparture.text = convertDateFormat(date)
                    selectedDate = convertDateFormat(date)
                    val editor = dateSharedPrefrens.edit()
                    editor.putString("startDate", selectedStartDate)
                    editor.putString("startDateFormat", selectedDate)
                    editor.apply()
                    Log.d(
                        "test", "Update $selectedStartDate"
                    )
                }
            }


            binding.dateReturn.setOnClickListener {
                if (switchMaterial.isChecked) {
                    showDatePicker(requireContext()) { date ->
                        selectedReturnDate = date
                        binding.tvPilihTanggal.text = convertDateFormat(date)
                        val editor = dateSharedPrefrens.edit()
                        editor.putString("returnDate", selectedReturnDate)
                        editor.apply()
                        Log.d(
                            "test", "Update $selectedReturnDate"
                        )
                    }
                }else {
                    val editor = dateSharedPrefrens.edit()
                    editor.remove("returnDate")
                    editor.apply()

                    binding.tvPilihTanggal.text = ""

                    Log.d(
                        "test", "Update $selectedReturnDate"
                    )
                }
            }

        }

        binding.flip.setOnClickListener {

            val currentDepartureText = binding.tvDeparture.text.toString()
            val currentArrivalText = binding.tvTujuan.text.toString()

            binding.tvDeparture.text = currentArrivalText
            binding.tvTujuan.text = currentDepartureText
        }

        // Isi listDestinasiFavorit dengan data destinasi favorit yang sesuai
        val viewModelFavorite = ViewModelProvider(this).get(FavoritDestinasiViewModel::class.java)
        viewModelFavorite.getFavorite()
        viewModelFavorite.livedataFavorite.observe(viewLifecycleOwner, Observer { favList ->
            if (favList != null) {
                val adapter = DestinasiFavoritAdapter(requireContext(), favList)
                binding.rvItemDestinasi.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.rvItemDestinasi.adapter = adapter
            }
        })


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
            findNavController().navigate(R.id.action_homeFragment_to_destinasiPencarianToFragment)
        }

        val dateSharedPrefrens = requireContext().getSharedPreferences("date", Context.MODE_PRIVATE)
        selectedStartDate = dateSharedPrefrens.getString("startDate", null)
        if (selectedStartDate == null) {
            selectedStartDate = selectedDate
            val editor = dateSharedPrefrens.edit()
            editor.putString("startDate", selectedStartDate)
            editor.putString("startDateFormat", selectedDate)
            editor.apply()
        }
        // Set onClickListener untuk dateDeparture
        binding.dateDeparture.setOnClickListener {
            showDatePicker(requireContext()) { date ->
                selectedStartDate = date
                binding.tvDateDeparture.text = convertDateFormat(date)
                selectedDate = convertDateFormat(date)
                val editor = dateSharedPrefrens.edit()

                editor.putString("startDateFormat", selectedDate)
                editor.putString("startDate", selectedStartDate)

                editor.apply()
                Log.d(
                    "home", "date $selectedStartDate"
                )
                Log.d(
                    "home", "date $selectedDate"
                )
            }
        }
        sharedPreferences.edit()
            .putInt("baby", 0)
            .putInt("child", 0)
            .putInt("adult", 1)
            .apply()

        updatePassengerCount()

        //digunakan untuk menerima kiriman data dari setclass
        parentFragmentManager.setFragmentResultListener("selected_class", viewLifecycleOwner) { _, result ->
            val className = result.getString("selected_class", "")
            val sharedPreferences = requireContext().getSharedPreferences("selected_class", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("selected_class", className)
            editor.apply()

            binding.tvBusiness.text = className
        }
        
        loadSelectedClass()

        var sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedDestination = sharedPreferences.getString("key", "")

        binding.tvDeparture.text = selectedDestination

        val sharedPreferencesTo = requireContext().getSharedPreferences("MyPrefsTo", Context.MODE_PRIVATE)
        val selectedDestinationTo = sharedPreferencesTo.getString("keyTo", "")

        binding.tvTujuan.text = selectedDestinationTo

        binding.btnPencarian.setOnClickListener {
            showLoading()
            if (isDataValid()) {
                // Mengambil nilai selectDestination dan selectDestinationTo

                sharedPreferences = requireContext().getSharedPreferences("passenger_counts", Context.MODE_PRIVATE)
                babyCount = sharedPreferences.getInt("baby", 0)
                childCount = sharedPreferences.getInt("child", 0)
                adultCount = sharedPreferences.getInt("adult", 0)
                val totalPassengers = childCount + adultCount

                // Simpan nilai total penumpang ke dalam Shared Preferences
                val editor = sharedPreferences.edit()
                editor.putInt("baby", babyCount)
                editor.putInt("child", childCount)
                editor.putInt("adult", adultCount)
                editor.putInt("totalPassengers", totalPassengers)
                editor.apply()

                // Buat bundle dan tambahkan data selectDestination dan selectDestinationTo
                if (selectedStartDate != null && selectedDestination != "Pilih Asal" && selectedDestinationTo != "Pilih Tujuan" ) {

                    val bundle = Bundle()
                    bundle.putString("selectDestination", selectedDestination)
                    bundle.putString("selectDestinationTo", selectedDestinationTo)
                    bundle.putString("startDateFormat", selectedDate)
                    bundle.putString("startDate", selectedStartDate)
                    bundle.putString("returnDate", selectedReturnDate)

                    Log.d("HomeFragment", "selectedDate: $selectedDate")
                    Log.d("HomeFragment", "selectedStartDate: $selectedStartDate")
                    Log.d("HomeFragment", "selectedReturnDate: $selectedReturnDate")
                    Log.d("HomeFragment", "selectedStartDate: $selectedDestinationTo")
                    Log.d("HomeFragment", "selectedReturnDate: $selectedDestination")

                // Jalankan penundaan selama 4 detik sebelum navigasi dilakukan
                Handler().postDelayed({
                    hideLoading()

                    // Navigasi ke fragment tujuan dengan menyertakan bundle
                    findNavController().navigate(R.id.action_homeFragment_to_hasilPencarianFragment, bundle)

                }, 1000)
                } else {
                    Toasty.warning(
                        requireContext(),
                        "Lengkapi Pencarian",
                        Toast.LENGTH_SHORT
                    ).show()
                    hideLoading()
                }// Penundaan selama 4 detik (4000 milidetik)
            } else {
                Toast.makeText(requireContext(), "Harap lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker(context: Context, onDateSelected: (date: String) -> Unit) {
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            ContextThemeWrapper(context, R.style.DateDialogTheme),
            null,
            initialYear,
            initialMonth,
            initialDay
        )

        datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val formattedDay = String.format("%02d", dayOfMonth)
            val formattedMonth = String.format("%02d", monthOfYear + 1)
            val date = "$formattedDay-$formattedMonth-$year"
            onDateSelected(date)
        }

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Oke") { _, _ ->
            val datePicker = datePickerDialog.datePicker
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val formattedDay = String.format("%02d", day)
            val formattedMonth = String.format("%02d", month + 1)
            val date = "$formattedDay-$formattedMonth-$year"
            onDateSelected(date)
        }

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal") { _, _ ->
            // Tidak ada tindakan yang perlu dilakukan ketika tombol "Batal" ditekan
        }

        datePickerDialog.setOnShowListener { dialog ->
            val positiveButton = (dialog as DatePickerDialog).getButton(DialogInterface.BUTTON_POSITIVE)
            val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)

            positiveButton.setTextColor(ContextCompat.getColor(context, R.color.darkblue_05))
            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.darkblue_05))
        }

        datePickerDialog.show()
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

    override fun onDestroyView() {
        super.onDestroyView()

        parentFragmentManager.clearFragmentResultListener("selected_class")
    }

    private fun updatePassengerCount() {
        babyCount = sharedPreferences.getInt("baby", 0)
        childCount = sharedPreferences.getInt("child", 0)
        adultCount = sharedPreferences.getInt("adult", 0)
        val totalPassengers = babyCount + childCount + adultCount

        // Simpan nilai total penumpang ke dalam Shared Preferences
        val editor = sharedPreferences.edit()
        editor.putInt("baby", babyCount)
        editor.putInt("child", childCount)
        editor.putInt("adult", adultCount)
        editor.putInt("totalPassengers", totalPassengers)
        editor.apply()

        // Mengubah jumlah penumpang menjadi teks yang sesuai
        val passengerText = "$totalPassengers penumpang"

        totalPassengerCount = totalPassengers
        binding.tvPenumpang.text = passengerText
    }

    //digunakan untuk menyimpan data setclas supaya tidak kreset
    private fun loadSelectedClass() {
        val sharedPreferences = requireContext().getSharedPreferences("selected_class", Context.MODE_PRIVATE)
        val className = sharedPreferences.getString("selected_class", "")

        binding.tvBusiness.text = className
    }

    private fun showLoading() {
        // Tampilkan efek loading di sini (misalnya dengan mengubah teks Button)
        binding.btnPencarian.text = "Loading..."
        binding.btnPencarian.isEnabled = false
    }

    private fun hideLoading() {
        // Sembunyikan efek loading di sini (misalnya dengan mengembalikan teks Button)
        binding.btnPencarian.text = "Cari Penerbangan"
        binding.btnPencarian.isEnabled = true
    }

    private fun isDataValid(): Boolean {
        val selectedStartDate = binding.tvDateDeparture.text.toString().trim()
        val selectedDeparture = binding.tvDeparture.text.toString().trim()
        val selectedDestination = binding.tvTujuan.text.toString().trim()

        if (selectedDeparture.isEmpty()) {
            Toast.makeText(requireContext(), "Pilih kota keberangkatan", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedDestination.isEmpty()) {
            Toast.makeText(requireContext(), "Pilih kota tujuan", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedStartDate.isEmpty()) {
            Toast.makeText(requireContext(), "Pilih tanggal keberangkatan", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }



}
