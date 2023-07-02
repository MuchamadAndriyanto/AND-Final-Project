package com.finalproject.tiketku.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.DestinasiAdapter
import com.finalproject.tiketku.adapter.DestinasiFavoritAdapter
import com.finalproject.tiketku.adapter.HariAdapter
import com.finalproject.tiketku.adapter.HasilPenerbanganAdapter
import com.finalproject.tiketku.adapter.JadwalTanggalAdapter
import com.finalproject.tiketku.adapter.NotifAdapter
import com.finalproject.tiketku.adapter.SetClassAdapter
import com.finalproject.tiketku.databinding.FragmentDestinasiPencarianBinding
import com.finalproject.tiketku.databinding.FragmentHasilPencarianBinding
import com.finalproject.tiketku.model.DummySetClass
import com.finalproject.tiketku.model.ListFilter
import com.finalproject.tiketku.model.ListHasilPencarian
import com.finalproject.tiketku.model.caripenerbangan.DataCariPenerbangan
import com.finalproject.tiketku.viewmodel.FavoritDestinasiViewModel
import com.finalproject.tiketku.viewmodel.JadwalViewModel
import com.finalproject.tiketku.viewmodel.NotifViewModel
import com.finalproject.tiketku.viewmodel.PencarianPenerbanganViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class HasilPencarianFragment : Fragment() {
    private lateinit var binding: FragmentHasilPencarianBinding
    private lateinit var classAdapter: HariAdapter
    private lateinit var classList: ArrayList<ListHasilPencarian>
    private var selectedFilter: String? = null
    private var selectedJadwal: String? = null

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var btnFilter2: Button

    private lateinit var hasilPenerbanganAdapter: HasilPenerbanganAdapter
    private lateinit var viewModelPencarianPenerbangan: PencarianPenerbanganViewModel
    private var tiketList: List<DataCariPenerbangan> = emptyList()

    companion object {
        const val REQUEST_CODE_FILTER = 123
        const val REQUEST_KEY_FILTER = "request_key_filter" // Kunci untuk menyimpan fragment hasil filter
        const val RESULT_KEY_FILTER = "result_key_filter" // Kunci untuk menyimpan hasil filter dalam Intent
        const val SHARED_PREFS_NAME = "MyPrefs" // Nama Shared Preferences
        const val FILTER_KEY = "filter_key" // Kunci untuk menyimpan data filter di Shared Preferences
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHasilPencarianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_hasilPencarianFragment_to_homeFragment)
        }

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        selectedFilter = sharedPreferences.getString(FILTER_KEY, null)

        btnFilter2 = binding.btnFilter2
        btnFilter2.text = selectedFilter

        btnFilter2.setOnClickListener {
            selectedFilter = sharedPreferences.getString(FILTER_KEY, null)
            applyFilter(selectedFilter)
            btnFilter2.text = selectedFilter
        }

        binding.btnFilter1.setOnClickListener {
            val filterFragment = HasilPencarianWithFilterFragment()
            filterFragment.setTargetFragment(this, REQUEST_CODE_FILTER)
            parentFragmentManager.beginTransaction()
                .add(filterFragment, REQUEST_KEY_FILTER)
                .commit()
        }

        hasilPenerbanganAdapter = HasilPenerbanganAdapter(requireContext(), tiketList)
        binding.rvTiket.adapter = hasilPenerbanganAdapter
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvTiket.layoutManager = layoutManager

        viewModelPencarianPenerbangan = ViewModelProvider(this).get(PencarianPenerbanganViewModel::class.java)
        viewModelPencarianPenerbangan.getFavorite()
        viewModelPencarianPenerbangan.livedataCariPenerbangan.observe(viewLifecycleOwner, Observer { favList ->
            tiketList = favList ?: emptyList()
            hasilPenerbanganAdapter.updateData(tiketList)
            applyFilter(selectedFilter)

            val viewModelJadwal = ViewModelProvider(this).get(JadwalViewModel::class.java)

            viewModelJadwal.getOnetrip()
            viewModelJadwal.livedataOnetrip.observe(viewLifecycleOwner, Observer { jadwalList ->
                if (jadwalList != null) {
                    val adapter = JadwalTanggalAdapter(requireContext(), jadwalList)
                    binding.rvHari.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.rvHari.adapter = adapter

                    val isTicketEmpty = tiketList.isEmpty()
                    val isJadwalEmpty = jadwalList.isEmpty()

                    if (isTicketEmpty && isJadwalEmpty) {
                        Toasty.warning(requireContext(), "Ticket dan Jadwal Tidak ditemukan", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_hasilPencarianFragment_to_noResultDetailFragment2)
                    } else if (isTicketEmpty) {
                        Toasty.warning(requireContext(), "Ticket Tidak ditemukan", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_hasilPencarianFragment_to_noResultDetailFragment2)
                    } else if (isJadwalEmpty) {
                        Toasty.warning(requireContext(), "Jadwal Tidak ditemukan", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_hasilPencarianFragment_to_noResultDetailFragment2)
                    }

                }
            })
        })
    }

    private fun applyFilter(filter: String?) {
        hasilPenerbanganAdapter.filterData(filter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FILTER && resultCode == Activity.RESULT_OK) {
            selectedFilter = data?.getStringExtra(RESULT_KEY_FILTER)
            if (selectedFilter != null) {
                applyFilter(selectedFilter)
                btnFilter2.text = selectedFilter
                // Simpan data filter ke SharedPreferences
                sharedPreferences.edit().putString(FILTER_KEY, selectedFilter).apply()
            }
        }
    }
}
