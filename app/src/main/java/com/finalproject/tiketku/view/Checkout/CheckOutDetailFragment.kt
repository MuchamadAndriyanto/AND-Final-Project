package com.finalproject.tiketku.view.Checkout

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentCheckOutDetailBinding
import com.finalproject.tiketku.model.rincianCO.DataDetailOrder
import com.finalproject.tiketku.view.DetailPenerbangan.DetailPenerbanganFragmentArgs
import com.finalproject.tiketku.view.DetailPenerbangan.DetailPenerbanganFragmentDirections
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutDetailFragment : Fragment() {

    lateinit var binding: FragmentCheckOutDetailBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var detailViewModel: OrderViewModel
    private var id: Int = 0 // Tambahkan variabel id

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        // Inisialisasi DetailViewModel
        detailViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)


        val token = sharedPref.getString("accessToken", "")
        if (token != null) {
//            id = CheckOutDetailFragmentArgs.fromBundle(requireArguments()).id
            detailViewModel.getOrders(id, token)
            Log.d("SelectSeatCheck", "id : $id ")
        } else {
            Toast.makeText(requireContext(), "Kosong", Toast.LENGTH_SHORT).show()
        }


        // Observer untuk liveDetailFavorite
        detailViewModel.liveDataDetailOrders.observe(viewLifecycleOwner, { detail ->
            if (detail != null) {
                binding.tvDeparture.text = detail.tiket.bandaraAwal.kota
                binding.tvDestination.text = detail.tiket.bandaraTujuan.kota
                binding.tvSelisihJam.text = detail.tiket.selisihJam.toString()
                binding.tvSelisihMenit.text = detail.tiket.selisihMenit.toString()
                binding.detailTime.text = detail.tiket.jamBerangkat
                binding.detailTimeArrived.text = detail.tiket.jamKedatangan
                binding.detailAirport.text = detail.tiket.bandaraAwal.namaBandara
                binding.detailAirportArrived.text = detail.tiket.bandaraTujuan.namaBandara
                binding.Maskapai.text = detail.tiket.maskapai.namaMaskapai
                binding.detailTotalHarga.text = detail.totalHargaTiket
                binding.tvHargaAdult.text = detail.tiket.maskapai.hargaTiket


                // Cek status login
                val sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
                val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

                if (isLoggedIn) {
                    binding.btnSubmit.setOnClickListener {
                        val action = DetailPenerbanganFragmentDirections.actionDetailPenerbanganFragmentToCheckoutBiodataPemesanFragment(id)
                        action.idPenerbangan = id // Mengirimkan id_penerbangan ke action
                        findNavController().navigate(action)
                    }
                } else {
                    binding.btnSubmit.setOnClickListener {
                        findNavController().navigate(R.id.action_detailPenerbanganFragment_to_akunNonLoginFragment)
                    }
                }

            } else {
                // Data detail tidak tersedia, lakukan sesuatu
                Toast.makeText(requireContext(), "Gagal memuat detail", Toast.LENGTH_SHORT).show()
            }
        })


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailPenerbanganFragment_to_homeFragment)
        }

    }
}
