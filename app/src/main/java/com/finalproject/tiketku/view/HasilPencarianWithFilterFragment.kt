package com.finalproject.tiketku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class HasilPencarianWithFilterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentHasilPencarianWithFilterBinding
    private lateinit var classList: ArrayList<ListFilter>
    private lateinit var classAdapter: FilterAdapter
    private var selectedClass: ListFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                // Store the selected data
                selectedClass = data
            }
        })

        binding.rvFilter.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = classAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}