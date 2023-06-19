package com.finalproject.tiketku.view.Beranda

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.DestinasiAdapter
import com.finalproject.tiketku.adapter.DestinasiToAdapter
import com.finalproject.tiketku.adapter.HistoriPencarianAdapter
import com.finalproject.tiketku.databinding.FragmentDestinasiPencarianBinding
import com.finalproject.tiketku.model.search.Data
import com.finalproject.tiketku.viewmodel.DestinasiViewModel

class DestinasiPencarianToFragment : Fragment() {
    private lateinit var binding: FragmentDestinasiPencarianBinding
    private lateinit var searchVM: DestinasiViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private val PREF_NAME = "SearchHistory"
    private val KEY_HISTORY = "search_history"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDestinasiPencarianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {

            findNavController().navigate(R.id.action_destinasiPencarianToFragment_to_homeFragment)
        }

        searchVM = ViewModelProvider(requireActivity()).get(DestinasiViewModel::class.java)

        binding.ivSearch.setOnClickListener {
            val kotaSearch = binding.etSearch.text.toString()
            getSearch(requireContext(), kotaSearch)
        }

        binding.tvHapus.setOnClickListener {
            clearSearchHistory(requireContext(), "kota")
        }

        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        showSearchHistory(requireContext())
    }

    private fun getSearch(context: Context, kota: String) {
        val searchText = binding.etSearch.text.toString().trim()
        if (searchText.isNotEmpty()) {
            searchVM.callGetSearch(kota)
            searchVM.search.observe(viewLifecycleOwner) { searchResults ->
                val filteredResults = searchResults?.filter { data ->
                    data.kota.contains(searchText, ignoreCase = true)
                }
                showSearch(context, filteredResults ?: emptyList())
                saveSearchHistory(searchText)
            }
        }
    }
    private fun showSearch(context: Context, data: List<Data>) {
        val searchAdapter = DestinasiToAdapter(context, data)
        binding.rvDestination.adapter = searchAdapter
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvDestination.layoutManager = layoutManager
    }

    private fun saveSearchHistory(searchText: String) {
        val searchHistory = getSearchHistory().toMutableList()
        searchHistory.add(searchText)

        val editor = sharedPreferences.edit()
        editor.putStringSet(KEY_HISTORY, searchHistory.toSet())
        editor.apply()
    }

    private fun getSearchHistory(): Set<String> {
        return sharedPreferences.getStringSet(KEY_HISTORY, emptySet()) ?: emptySet()
    }

    private fun showSearchHistory(context: Context) {
        val searchHistory = getSearchHistory().toList()
        val searchHistoryAdapter = HistoriPencarianAdapter(context, searchHistory)
        binding.rvDestination.adapter = searchHistoryAdapter
        binding.rvDestination.layoutManager = LinearLayoutManager(context)

        searchHistoryAdapter.onItemClick = { searchText ->
            binding.etSearch.setText(searchText)
            getSearch(context, searchText)
        }
    }

    private fun clearSearchHistory(context: Context, kota: String) {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_HISTORY)
        editor.apply()

        // Menghapus riwayat pencarian
        showSearchHistory(context)
    }
}