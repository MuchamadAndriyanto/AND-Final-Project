package com.finalproject.tiketku.view.Beranda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.adapter.DestinasiAdapter
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentDestinasiPencarianBinding
import com.finalproject.tiketku.model.BandaraAwal
import com.finalproject.tiketku.model.search.Data
import com.finalproject.tiketku.viewmodel.DestinasiViewModel
import com.finalproject.tiketku.viewmodel.UsersViewModel

class DestinasiPencarianFragment : Fragment() {
    private lateinit var binding: FragmentDestinasiPencarianBinding
    private lateinit var searchVM: DestinasiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDestinasiPencarianBinding.inflate(layoutInflater, container, false)
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
            val titleSearch = binding.etSearch.text.toString()
            getSearch(titleSearch)
        }
    }

    fun getSearch(kota: String) {
        if (binding.etSearch.text.toString().isNotEmpty()) {
            searchVM.callGetSearch(kota)
            searchVM.search.observe(viewLifecycleOwner) {
                if (it != null) {
                    showSearch(it)
                }
            }
        }
    }

    fun showSearch(data: List<Data>) {
        val searchAdapter = DestinasiAdapter(data)
        binding.rvDestination.adapter = searchAdapter
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvDestination.layoutManager = layoutManager


    }
}