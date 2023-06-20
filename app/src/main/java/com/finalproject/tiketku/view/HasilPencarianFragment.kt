package com.finalproject.tiketku.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.finalproject.tiketku.adapter.SetClassAdapter
import com.finalproject.tiketku.databinding.FragmentDestinasiPencarianBinding
import com.finalproject.tiketku.databinding.FragmentHasilPencarianBinding
import com.finalproject.tiketku.model.DummySetClass
import com.finalproject.tiketku.model.ListFilter
import com.finalproject.tiketku.model.ListHasilPencarian
import com.finalproject.tiketku.viewmodel.FavoritDestinasiViewModel
import com.finalproject.tiketku.viewmodel.PencarianPenerbanganViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HasilPencarianFragment : Fragment() {
    private lateinit var binding: FragmentHasilPencarianBinding
    private lateinit var classAdapter: HariAdapter
    private lateinit var classList: ArrayList<ListHasilPencarian>
    private var selectedFilter: String? = null

    lateinit var btnFilter2: Button

    companion object {
        const val REQUEST_CODE_FILTER = 123
        const val REQUEST_KEY_FILTER = "request_key_filter" // Kunci untuk menyimpan fragment hasil filter
        const val RESULT_KEY_FILTER = "result_key_filter" // Kunci untuk menyimpan hasil filter dalam Intent
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

        classList = ArrayList()
        classList.add(ListHasilPencarian("Senin", "00/00/00"))
        classList.add(ListHasilPencarian("Selasa", "00/00/00"))
        classList.add(ListHasilPencarian("Rabu", "00/00/00"))
        classList.add(ListHasilPencarian("Kamis", "00/00/00"))
        classList.add(ListHasilPencarian("Jumat", "00/00/00"))
        classList.add(ListHasilPencarian("Sabtu", "00/00/00"))
        classList.add(ListHasilPencarian("Minggu", "00/00/00"))

        val selected = 0

        classAdapter = HariAdapter(classList)
        classAdapter.setOnItemClickCallback(object : HariAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int, data: ListHasilPencarian) {
                selectedFilter = data.hari
            }
        })

        binding.rvHari.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = classAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_hasilPencarianFragment_to_homeFragment)
        }

        binding.btnFilter1.setOnClickListener {
            val filterFragment = HasilPencarianWithFilterFragment() // Membuat instance dari fragment HasilPencarianWithFilterFragment
            filterFragment.setTargetFragment(this, REQUEST_CODE_FILTER) // Mengatur fragment target untuk menerima hasil dari fragment filter
            parentFragmentManager.beginTransaction()
                .add(filterFragment, REQUEST_KEY_FILTER)
                .commit()
        }


        btnFilter2 = binding.root.findViewById(R.id.btnFilter2)
        btnFilter2.setOnClickListener {
            if (selectedFilter != null) {
                btnFilter2.text = selectedFilter // Memperbarui teks tombol btnFilter2 dengan hasil filter
            }
        }


        val viewModelFavorite = ViewModelProvider(this).get(PencarianPenerbanganViewModel::class.java)
        viewModelFavorite.getFavorite()
        viewModelFavorite.livedataFavorite.observe(viewLifecycleOwner, Observer { favList ->
            if (favList != null) {
                val searchAdapter = HasilPenerbanganAdapter(requireContext(), favList)
                binding.rvTiket.adapter = searchAdapter
                val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                binding.rvTiket.layoutManager = layoutManager
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FILTER && resultCode == Activity.RESULT_OK) {
            selectedFilter = data?.getStringExtra(RESULT_KEY_FILTER) // Mengambil hasil filter dari Intent menggunakan kunci RESULT_KEY_FILTER
            if (selectedFilter != null) {
                btnFilter2.text = selectedFilter // Memperbarui teks tombol btnFilter2 dengan hasil filter
            }
        }
    }





}

