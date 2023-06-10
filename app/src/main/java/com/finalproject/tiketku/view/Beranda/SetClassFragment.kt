package com.finalproject.tiketku.view.Beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        classList.add(DummySetClass("Kelas 1","Rp.20000"))
        classList.add(DummySetClass("Kelas 2","Rp.30000"))
        classList.add(DummySetClass("Kelas 3","Rp.40000"))
        classList.add(DummySetClass("Kelas 4","Rp.50000"))

        val selected = 0

        binding.rvSetClass.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
            classAdapter = SetClassAdapter (classList,selected)
            adapter = classAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

    }

}