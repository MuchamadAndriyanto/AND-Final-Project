package com.finalproject.tiketku.view.Beranda

import android.content.Intent
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
            getSearch(kotaSearch)
        }
    }

    fun getSearch(kota: String) {
        if (binding.etSearch.text.isNotEmpty()) {
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

/*        searchAdapter.onClick = { item ->
            val idNowPlaying = item.id
            val intent = Intent(context, HomeFragment::class.java)
            intent.putExtra("ID", idNowPlaying.toString())
            context.startActivity(intent)
        }*/

        searchAdapter.onClick = {item ->
            val kota = item.kota
            clik(kota)
        }

    }

    private fun clik(kota: String){
        binding.rvDestination.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDestination.setHasFixedSize(false)
        searchVM.callGetSearch(kota)
        searchVM.search.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvDestination.adapter = DestinasiAdapter(it)
            }
        }
    }

}