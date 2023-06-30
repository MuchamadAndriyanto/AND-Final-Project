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
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.tiketku.adapter.DetailRoundtripPergiAdapter
import com.finalproject.tiketku.adapter.DetailRoundtripPulangAdapter
import com.finalproject.tiketku.databinding.FragmentDetailPenerbanganRoundtripBinding
import com.finalproject.tiketku.viewmodel.DetailRoundtripViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPenerbanganRoundtripFragment : Fragment() {

    private lateinit var binding: FragmentDetailPenerbanganRoundtripBinding
    private lateinit var viewModelDetailRoundtrip: DetailRoundtripViewModel
    private lateinit var viewModelDetailRoundtripPulang: DetailRoundtripViewModel
    private lateinit var adapter: DetailRoundtripPergiAdapter

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

        viewModelDetailRoundtrip = ViewModelProvider(this).get(DetailRoundtripViewModel::class.java)

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

//        viewModelDetailRoundtripPulang.getDetailRoundtripPulang(idPulang)
//
//        viewModelDetailRoundtripPulang.livedetailPulang.observe(viewLifecycleOwner, Observer { detailRoundtrip ->
//            if (detailRoundtrip != null) {
//                adapter = DetailRoundtripPulangAdapter(requireContext(), detailRoundtrip)
//                binding.rvDetailRountripPulang.layoutManager = LinearLayoutManager(requireContext())
//                binding.rvDetailRountripPulang.adapter = adapter
//            } else {
//                // Handle error case
//            }
//        })
    }
}
