package com.finalproject.tiketku.view.Checkout

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentCheckoutBiodataPemesanBinding
import com.finalproject.tiketku.model.order.DataOrder
import com.finalproject.tiketku.viewmodel.DetailViewModel
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutBiodataPemesanFragment : Fragment() {
    lateinit var binding: FragmentCheckoutBiodataPemesanBinding
    private lateinit var viewModel: OrderViewModel
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBiodataPemesanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi DetailViewModel
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        // Inisialisasi DetailViewModel
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
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

        // Mengambil data detail
        val args: CheckoutBiodataPemesanFragmentArgs by navArgs()
        val idPenerbangan: Int = args.idPenerbangan
        detailViewModel.getDetail(idPenerbangan)

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
            editor.putInt("idPenerbangan", idPenerbangan)
            editor.putString("jumlahPenumpang", jumlahPenumpang)
            editor.putString("namaKeluarga", nama_keluarga)
            editor.putString("namaLengkap", nama_lengkap)
            editor.putString("noTelp", nomorTelepon)
            editor.apply()

            Log.d("SelectSeatCheckOut", "data : $email, $idPenerbangan, $jumlahPenumpang, $nama_keluarga, $nama_lengkap, $nomorTelepon")

            val bundle = Bundle()
            bundle.putInt("idPenerbangan", sharedPref.getInt("idPenerbangan", 0))
            findNavController().navigate(R.id.action_checkoutBiodataPemesanFragment_to_selectSeatFragment, bundle)

        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutBiodataPemesanFragment_to_detailPenerbanganFragment)
        }
    }
}
