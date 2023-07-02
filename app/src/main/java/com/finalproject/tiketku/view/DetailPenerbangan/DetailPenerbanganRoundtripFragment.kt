package com.finalproject.tiketku.view.DetailPenerbangan

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.DetailRoundtripPergiAdapter
import com.finalproject.tiketku.adapter.DetailRoundtripPulangAdapter
import com.finalproject.tiketku.databinding.FragmentDetailPenerbanganRoundtripBinding
import com.finalproject.tiketku.viewmodel.DetailRoundtripPulangViewModel
import com.finalproject.tiketku.viewmodel.DetailRoundtripViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPenerbanganRoundtripFragment : Fragment() {

    private lateinit var binding: FragmentDetailPenerbanganRoundtripBinding
    private lateinit var viewModelDetailRoundtrip: DetailRoundtripViewModel
    private lateinit var viewModelDetailRoundtripPulang: DetailRoundtripPulangViewModel
    private lateinit var adapter: DetailRoundtripPergiAdapter
    private lateinit var adapterPulang: DetailRoundtripPulangAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPenerbanganRoundtripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kotafrom: SharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val kotato: SharedPreferences = requireContext().getSharedPreferences("MyPrefsTo", Context.MODE_PRIVATE)
        val fromDatePref = kotafrom.getString("key", "")
        val toDatePref = kotato.getString("keyTo", "")


        var from = fromDatePref
        var to = toDatePref

        binding.tvDeparture.text = from
        binding.tvDeparturePulang.text = to
        binding.tvDestination.text = to
        binding.tvDestinationPulang.text = from

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSubmit.setOnClickListener {
            val jadwal: SharedPreferences = requireContext().getSharedPreferences("IDPrefs", Context.MODE_PRIVATE)
            val idDatePref = jadwal.getInt("selected_id_pergi", 0)
            val idPulangDatePref = jadwal.getInt("selected_id_pulang", 0)

            val editor = jadwal.edit()
            editor.putInt("selected_id_pergi", idDatePref)
            editor.putInt("selected_id_pulang", idPulangDatePref)
            findNavController().navigate(R.id.action_detailPenerbanganRoundtripFragment_to_checkoutBiodataPemesanRoundTrip)
        }

        viewModelDetailRoundtrip = ViewModelProvider(this).get(DetailRoundtripViewModel::class.java)
        viewModelDetailRoundtripPulang = ViewModelProvider(this).get(DetailRoundtripPulangViewModel::class.java)

        val jadwal: SharedPreferences = requireContext().getSharedPreferences("IDPrefs", Context.MODE_PRIVATE)
        val idDatePref = jadwal.getInt("selected_id_pergi", 0).toString()
        val idPulangDatePref = jadwal.getInt("selected_id_pulang", 0).toString()



        val idPergi = idDatePref?.toIntOrNull() ?: 0
        val idPulang = idPulangDatePref?.toIntOrNull() ?: 0

        viewModelDetailRoundtrip.getDetailRoundtrip(idPergi)

        viewModelDetailRoundtrip.livedetailPergi.observe(viewLifecycleOwner, Observer { detailRoundtrip ->
            if (detailRoundtrip != null) {
                adapter = DetailRoundtripPergiAdapter(requireContext(), detailRoundtrip)
                binding.rvDetailRountrip.layoutManager = LinearLayoutManager(requireContext())
                binding.rvDetailRountrip.adapter = adapter
            } else {
                // Handle error case
            }
        })

        viewModelDetailRoundtripPulang.getDetailRoundtripPulang(idPulang)

        viewModelDetailRoundtripPulang.livedetailPulang.observe(viewLifecycleOwner, Observer { detailRoundtripPulang ->
            if (detailRoundtripPulang != null) {
                adapterPulang = DetailRoundtripPulangAdapter(requireContext(), detailRoundtripPulang)
                binding.rvDetailRountripPulang.layoutManager = LinearLayoutManager(requireContext())
                binding.rvDetailRountripPulang.adapter = adapterPulang
            } else {
                // Handle error case
            }
        })
    }
}
