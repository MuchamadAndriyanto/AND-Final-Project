package com.finalproject.tiketku.view.Beranda

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.SetClassAdapter
import com.finalproject.tiketku.databinding.FragmentSetClassBinding
import com.finalproject.tiketku.model.DummySetClass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SetClassFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSetClassBinding
    private lateinit var classList: ArrayList<DummySetClass>
    private lateinit var classAdapter: SetClassAdapter

    private var selectedClass: DummySetClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        classList = ArrayList()
        classList.add(DummySetClass("Economy", "Rp.20000"))
        classList.add(DummySetClass("Premium Economy", "Rp.30000"))
        classList.add(DummySetClass("Business", "Rp.40000"))
        classList.add(DummySetClass("First Class", "Rp.50000"))

        val selected = 0

        classAdapter = SetClassAdapter(classList)
        classAdapter.setOnItemClickCallback(object : SetClassAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int, data: DummySetClass) {
                selectedClass = data
            }
        })

        binding.rvSetClass.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = classAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        binding.btnSimpan.setOnClickListener {
            selectedClass?.let {
                saveSelectedClass(it)
                dismiss()
            }
        }
    }
    private fun saveSelectedClass(selectedClass: DummySetClass) {
        val result = Bundle().apply {
            putString("selected_class", selectedClass.kelas)
        }
        setFragmentResult("selected_class", result)
        dismiss()
    }
}

