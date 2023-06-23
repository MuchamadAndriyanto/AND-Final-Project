package com.finalproject.tiketku.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.FilterAdapter
import com.finalproject.tiketku.adapter.HariAdapter
import com.finalproject.tiketku.adapter.SetClassAdapter
import com.finalproject.tiketku.databinding.FragmentHasilPencarianBinding
import com.finalproject.tiketku.databinding.FragmentHasilPencarianWithFilterBinding
import com.finalproject.tiketku.databinding.FragmentSetPenumpangBinding
import com.finalproject.tiketku.model.DummySetClass
import com.finalproject.tiketku.model.ListFilter
import com.finalproject.tiketku.model.ListHasilPencarian
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HasilPencarianWithFilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHasilPencarianWithFilterBinding
    private lateinit var classList: ArrayList<ListFilter>
    private lateinit var classAdapter: FilterAdapter
    private var selectedClass: ListFilter? = null

    companion object {
        const val RESULT_KEY_FILTER = "result_key_filter" // Kunci untuk menyimpan fragment hasil filter
        const val REQUEST_CODE_FILTER = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHasilPencarianWithFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        classList = ArrayList()
        classList.add(ListFilter("Harga", "Termurah"))
        classList.add(ListFilter("Durasi", "Terpendek"))
        classList.add(ListFilter("Keberangkatan", "Paling Akhir"))
        classList.add(ListFilter("Keberangkatan", "Paling Awal"))
        classList.add(ListFilter("Kedatangan", "Paling Akhir"))
        classList.add(ListFilter("Kedatangan", "Paling Awal"))

        val selected = 0

        classAdapter = FilterAdapter(classList)
        classAdapter.setOnItemClickCallback(object : FilterAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int, data: ListFilter) {
                selectedClass = data
            }
        })

        binding.rvFilter.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = classAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        binding.btnSimpan.setOnClickListener {
            if (selectedClass != null) {
                saveData(selectedClass!!)

            } else {
                Toast.makeText(requireContext(), "Pilih satu opsi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveData(selectedData: ListFilter) {
        val intent = Intent()
        intent.putExtra(RESULT_KEY_FILTER, selectedData.text2) // Menyimpan hasil filter dalam Intent dengan kunci RESULT_KEY_FILTER
        targetFragment?.onActivityResult(REQUEST_CODE_FILTER, Activity.RESULT_OK, intent) // Mengirimkan hasil filter ke fragment tujuan menggunakan onActivityResult
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Memeriksa apakah permintaan kode dan hasil sesuai dengan permintaan filter
        if (requestCode == REQUEST_CODE_FILTER && resultCode == Activity.RESULT_OK) {
            // Mengambil hasil filter dari Intent dan hasil filter tersebut digunakan untuk memperbarui teks tombol btnFilter2
            val selectedFilter = data?.getStringExtra(RESULT_KEY_FILTER)
            if (selectedFilter != null) {
                val parentFragment = targetFragment as? HasilPencarianFragment
                parentFragment?.btnFilter2?.text = selectedFilter
            }
        }
    }
}













