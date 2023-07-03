package com.finalproject.tiketku.view.Checkout

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentSelectSeatRoundTripBinding
import com.finalproject.tiketku.model.orderRoundTrip.DataOrderRT
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSeatRoundTripFragment : Fragment() {
    private lateinit var binding: FragmentSelectSeatRoundTripBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var viewModel: OrderViewModel
    private var selectedSeatId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectSeatRoundTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSubmit.setOnClickListener {
            sharedPrefs = requireContext().getSharedPreferences("biodata", Context.MODE_PRIVATE)

            val email = sharedPrefs.getString("email", "")
            val idPenerbanganBerangkat = sharedPrefs.getInt("selected_id_pergi", 0)
            val idPenerbanganPulang = sharedPrefs.getInt("selected_id_pulang", 0)
            val jumlahPenumpang = sharedPrefs.getString("jumlahPenumpang", "")
            val namaKeluarga = sharedPrefs.getString("namaKeluarga", "")
            val namaLengkap = sharedPrefs.getString("namaLengkap", "")
            val noTelp = sharedPrefs.getString("noTelp", "")

            if (selectedSeatId != null) {
                if (email != null && jumlahPenumpang != null && namaKeluarga != null && namaLengkap != null && noTelp != null) {
                    processBooking(
                        email,
                        idPenerbanganBerangkat,
                        idPenerbanganPulang,
                        jumlahPenumpang,
                        selectedSeatId!!,
                        namaKeluarga,
                        namaLengkap,
                        noTelp
                    )
                    Log.d(
                        "SelectSeatCheck",
                        "data : $selectedSeatId, $email, $idPenerbanganPulang, $jumlahPenumpang, $namaKeluarga, $namaLengkap, $noTelp"
                    )

                    viewModel.isOrderIdAvailable.observe(viewLifecycleOwner) { isAvailable ->
                        if (isAvailable) {
                            val bundle = Bundle()
                            bundle.putInt("orderIdRT", viewModel.getOrderIdRT()!!)
                            bundle.putString("email", email)
                            bundle.putInt("selected_id_pergi", idPenerbanganBerangkat)
                            bundle.putInt("selected_id_pulang", idPenerbanganPulang)
                            bundle.putString("jumlahPenumpang", jumlahPenumpang)
                            bundle.putString("namaKeluarga", namaKeluarga)
                            bundle.putString("namaLengkap", namaLengkap)
                            bundle.putString("noTelp", noTelp)

                            findNavController().navigate(
                                R.id.action_selectSeatRoundTripFragment_to_checkOutDetailRoundTripFragment,
                                bundle
                            )
                        } else {
                            Toast.makeText(requireContext(), "Kursi sudah terisi", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal pesan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Pilih kursi terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        val seatButtons = binding.seatGrid.children.toList()

        for (button in seatButtons) {
            button.setOnClickListener {
                val resourceId = button.id
                val resourceName = resources.getResourceName(resourceId)
                val seatId = resourceName.substringAfter("/")

                toggleSeatSelection(button, seatId)
                // Gunakan seatId sesuai kebutuhan Anda
                Log.d("ClickedSeatId", seatId)
            }
        }
    }

    private fun toggleSeatSelection(button: View, seatId: String) {
        if (selectedSeatId == seatId) {
            // Membatalkan pemilihan kursi jika tombol kursi yang sama diklik lagi
            selectedSeatId = null
            updateButtonAppearance(button, false)
        } else {
            // Memilih kursi baru
            if (selectedSeatId != null) {
                // Membatalkan pemilihan kursi sebelumnya
                val previousButton = binding.seatGrid.findViewWithTag<View>(selectedSeatId)
                if (previousButton != null) {
                    // Membatalkan pemilihan kursi sebelumnya
                    updateButtonAppearance(previousButton, false)
                }
            }

            selectedSeatId = seatId
            updateButtonAppearance(button, true)
        }

        Log.d("SelectSeatCheck", "Kursi terpilih: $selectedSeatId")
    }

    private fun updateButtonAppearance(button: View, isSelected: Boolean) {
        button.isSelected = isSelected
        val colorResId = if (isSelected) R.color.darkblue_03 else R.color.grey
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), colorResId))
    }

    private fun processBooking(
        email: String,
        idPenerbanganBerangkat: Int,
        idPenerbanganPulang: Int,
        jumlahPenumpang: String?,
        selectedSeatId: String,
        namaKeluarga: String,
        namaLengkap: String,
        noTelp: String
    ) {
        val token = sharedPref.getString("accessToken", "")
        val jumlahPenumpangInt = jumlahPenumpang?.toIntOrNull() ?: 0 // Ubah null menjadi 0 jika jumlahPenumpang null

        val dataOrder = DataOrderRT(
            0,
            idPenerbanganBerangkat,
            idPenerbanganPulang,
            email,
            jumlahPenumpangInt,
            selectedSeatId,
            namaKeluarga,
            namaLengkap,
            noTelp
        )

        if (!token.isNullOrEmpty()) {
            viewModel.postOrdersRT(token, dataOrder)
            binding.btnBack.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("selected_id_pergi", idPenerbanganBerangkat)
                bundle.putInt("selected_id_pulang", idPenerbanganPulang)
                findNavController().navigate(
                    R.id.action_selectSeatRoundTripFragment_to_checkOutDetailRoundTripFragment,
                    bundle
                )
            }
        } else {
            Toast.makeText(requireContext(), "Token kosong", Toast.LENGTH_SHORT).show()
        }
    }

}
