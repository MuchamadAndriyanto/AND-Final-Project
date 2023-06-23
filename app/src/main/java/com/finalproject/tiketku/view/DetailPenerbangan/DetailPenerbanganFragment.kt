package com.finalproject.tiketku.view.DetailPenerbangan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentDetailPenerbanganBinding
import com.finalproject.tiketku.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPenerbanganFragment : Fragment() {

    private lateinit var binding: FragmentDetailPenerbanganBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPenerbanganBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi DetailViewModel
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        // Mengambil data detail
        val args: DetailPenerbanganFragmentArgs by navArgs()
        var id = args.id
        detailViewModel.getDetail(id)

        // Observer untuk liveDetailFavorite
        detailViewModel.liveDetailFavorite.observe(viewLifecycleOwner, { detail ->
            if (detail != null) {
                // Data detail tersedia, lakukan sesuatu
                binding.tvDeparture.text = detail.id_bandara_asal
                binding.tvDestination.text = detail.id_bandara_tujuan

                binding.tvSelisihJam.setText("(" + detail.selisih_jam + "h - ");
                binding.tvSelisihMenit.setText(detail.selisih_menit.toString() + "m)");

                binding.detailTime.text = detail.jam_berangkat
                binding.detailDate.text = detail.tanggal_berangkat
                binding.detailAirport.text = detail.bandaraAwal.nama_bandara

                binding.Maskapai.text = detail.maskapai.nama_maskapai

                binding.detailTimeArrived.text = detail.jam_kedatangan
                binding.detailAirportArrived.text = detail.bandaraTujuan.nama_bandara

                binding.detailTotalDec.text = detail.maskapai.harga_tiket

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

        arguments?.let {
            val safeArgs = DetailPenerbanganFragmentArgs.fromBundle(it)
            id = safeArgs.id
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailPenerbanganFragment_to_homeFragment)
        }

    }
}
