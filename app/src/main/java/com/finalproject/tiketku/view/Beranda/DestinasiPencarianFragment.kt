package com.finalproject.tiketku.view.Beranda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.adapter.DestinasiAdapter
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentDestinasiPencarianBinding
import com.finalproject.tiketku.model.BandaraAwal
import com.finalproject.tiketku.model.DummySetClass
import com.finalproject.tiketku.model.search.Data
import com.finalproject.tiketku.viewmodel.DestinasiViewModel
import com.finalproject.tiketku.viewmodel.UsersViewModel

class DestinasiPencarianFragment : Fragment() {
    private lateinit var binding: FragmentDestinasiPencarianBinding
    private lateinit var searchVM: DestinasiViewModel

    private var selectedClass: Data? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDestinasiPencarianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_destinasiPencarianFragment_to_homeFragment)
        }
        /*searchVM = ViewModelProvider(this).get(DestinasiViewModel::class.java)*/
        searchVM = ViewModelProvider(requireActivity()).get(DestinasiViewModel::class.java)
        binding.ivSearch.setOnClickListener {
            val kotaSearch = binding.etSearch.text.toString()

            getSearch(requireContext(), kotaSearch)
        }


    }

    /*fun getSearch(context: Context,kota: String) {
        if (binding.etSearch.text.toString().isNotEmpty()) {
            searchVM.callGetSearch(kota)
            searchVM.search.observe(viewLifecycleOwner) {
                if (it != null) {
                    showSearch(context,it)
                }
            }
        }
    }*/

    fun getSearch(context: Context, kota: String) {
        val searchText = binding.etSearch.text.toString().trim()
        if (searchText.isNotEmpty()) {
            searchVM.callGetSearch(kota)
            searchVM.search.observe(viewLifecycleOwner) { searchResults ->
                val filteredResults = searchResults?.filter { data ->
                    // Ubah logika pencocokan di sini sesuai kebutuhan Anda
                    data.kota.contains(searchText, ignoreCase = true)
                }
                showSearch(context, filteredResults ?: emptyList())
            }
        }
    }

    fun showSearch(context: Context,data: List<Data>) {
        val searchAdapter = DestinasiAdapter(context,data)
        binding.rvDestination.adapter = searchAdapter
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvDestination.layoutManager = layoutManager

/*        searchAdapter.onClick = { item ->
            val idNowPlaying = item.id
            val intent = Intent(context, HomeFragment::class.java)
            intent.putExtra("ID", idNowPlaying.toString())
            context.startActivity(intent)
        }*/

        searchAdapter.onClick = {item ->
            val kota = item.kota
            clik(context,kota)
        }

    }

    private fun clik(context: Context,kota: String){

        binding.rvDestination.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDestination.setHasFixedSize(false)
        searchVM.callGetSearch(kota)
        searchVM.search.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvDestination.adapter = DestinasiAdapter(context,it)

            }
        }
    }

    private fun saveSelectedClass(selectedClass: Data) {
        val sharedPreferences = requireContext().getSharedPreferences("search", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("search", selectedClass.kota)
        editor.apply()

        setFragmentResult("search", bundleOf("search" to selectedClass.kota))

    }

}