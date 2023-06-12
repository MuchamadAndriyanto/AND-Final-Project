package com.finalproject.tiketku.view.Beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        // Inflate the layout for this fragment
        binding = FragmentSetClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        classList = ArrayList()
        classList.add(DummySetClass("Kelas 1", "Rp.20000"))
        classList.add(DummySetClass("Kelas 2", "Rp.30000"))
        classList.add(DummySetClass("Kelas 3", "Rp.40000"))
        classList.add(DummySetClass("Kelas 4", "Rp.50000"))

        val selected = 0

        classAdapter = SetClassAdapter(classList)
        classAdapter.setOnItemClickCallback(object : SetClassAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int, data: DummySetClass) {
                // Store the selected data
                selectedClass = data
            }
        })

        binding.rvSetClass.apply {
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

        binding.btnSimpan.setOnClickListener {
            selectedClass?.let {
                Toast.makeText(requireContext(), "Data saved: ${it.kelas}", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}
