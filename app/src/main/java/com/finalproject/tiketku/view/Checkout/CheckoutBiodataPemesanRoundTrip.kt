@file:Suppress("LocalVariableName", "LocalVariableName", "LocalVariableName", "LocalVariableName",
    "LocalVariableName", "LocalVariableName", "LocalVariableName"
)

package com.finalproject.tiketku.view.Checkout

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentCheckoutBiodataPemesanRoundTripBinding
import com.finalproject.tiketku.viewmodel.DetailViewModel
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("LocalVariableName")
@AndroidEntryPoint
class CheckoutBiodataPemesanRoundTrip : Fragment() {
    lateinit var binding: FragmentCheckoutBiodataPemesanRoundTripBinding
    private lateinit var viewModel: OrderViewModel
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBiodataPemesanRoundTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi DetailViewModel
        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        // Inisialisasi DetailViewModel
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        // Ambil token dari SharedPreferences
        token = sharedPref.getString("accessToken", "") ?: ""

        val switchMaterial = binding.switchMaterial
        val linear_nama_keluarga = binding.lin4
        val nama_keluarga_switch = binding.inputNamaKeluarga

        // Switch material
        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            val thumbTint = if (isChecked) {
                ContextCompat.getColor(requireContext(), R.color.purple)
            } else {
                ContextCompat.getColor(requireContext(), R.color.white)
            }
            if (isChecked) {
                linear_nama_keluarga.visibility = View.VISIBLE
            } else {
                linear_nama_keluarga.visibility = View.GONE
                nama_keluarga_switch.text = null
            }

            switchMaterial.thumbTintList = ColorStateList.valueOf(thumbTint)
        }

//        // Mengambil data detail
//        val args: CheckoutBiodataPemesanFragmentArgs by navArgs()
//        var idPenerbangan: Int = args.idPenerbangan
//
//        // Periksa apakah idPenerbangan ada dalam args
//        if (idPenerbangan == 0) {
//            // Jika tidak ada dalam args, periksa apakah ada dalam SharedPreferences
//            idPenerbangan = sharedPref.getInt("idPenerbangan", 0)
//
//        } else {
//            // Jika ada dalam args, simpan nilainya ke SharedPreferences untuk penggunaan selanjutnya
//            val editor = sharedPref.edit()
//            editor.putInt("idPenerbangan", idPenerbangan)
//            editor.apply()
//        }

//
//        // Mengambil data detail jika idPenerbangan ada
//        if (idPenerbangan != 0) {
//            detailViewModel.getDetail(idPenerbangan)
//        } else {
//            Toasty.error(requireContext(), "Penerbangan belum dipilih", Toast.LENGTH_SHORT, true).show()
//        }

        val jadwal: SharedPreferences = requireContext().getSharedPreferences("IDPrefs", Context.MODE_PRIVATE)
        val idDatePref = jadwal.getInt("selected_id_pergi", 0)
        val idPulangDatePref = jadwal.getInt("selected_id_pulang", 0)

        binding.btnSubmit.setOnClickListener {
            val nama_lengkap = binding.inputNamaLengkap.text.toString()
            val nama_keluarga = binding.inputNamaKeluarga.text.toString()
            val nomorTelepon = binding.inputNoTelp.text.toString()
            val email = binding.inputEmail.text.toString()


            val sharedPreferences = requireContext().getSharedPreferences("passenger_counts", Context.MODE_PRIVATE)

            // Mendapatkan nilai default untuk jumlahPenumpang
            val defaultJumlahPenumpang = 1

            // Mendapatkan nilai jumlahPenumpang dari Shared Preferences
            val jumlahPenumpang = sharedPreferences.getInt("totalPassengers", defaultJumlahPenumpang).toString()

            sharedPrefs = requireContext().getSharedPreferences("biodata", Context.MODE_PRIVATE)

            // Simpan data ke SharedPreferences
            val editor = sharedPrefs.edit()
            editor.putString("email", email)
            editor.putInt("selected_id_pergi", idDatePref)
            editor.putInt("selected_id_pulang", idPulangDatePref)
            editor.putString("jumlahPenumpang", jumlahPenumpang)
            editor.putString("namaKeluarga", nama_keluarga)
            editor.putString("namaLengkap", nama_lengkap)
            editor.putString("noTelp", nomorTelepon)
            editor.apply()

            Log.d("SelectSeatCheckOut", "data : $email, $idPulangDatePref, $idDatePref, $jumlahPenumpang, $nama_keluarga, $nama_lengkap, $nomorTelepon")


            findNavController().navigate(R.id.action_checkoutBiodataPemesanRoundTrip_to_selectSeatRoundTripFragment)

        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}
