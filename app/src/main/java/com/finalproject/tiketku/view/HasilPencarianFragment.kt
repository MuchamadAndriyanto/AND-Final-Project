package com.finalproject.tiketku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.adapter.HariAdapter
import com.finalproject.tiketku.adapter.SetClassAdapter
import com.finalproject.tiketku.databinding.FragmentDestinasiPencarianBinding
import com.finalproject.tiketku.databinding.FragmentHasilPencarianBinding
import com.finalproject.tiketku.model.DummySetClass
import com.finalproject.tiketku.model.ListHasilPencarian


class HasilPencarianFragment : Fragment() {
    private lateinit var binding: FragmentHasilPencarianBinding
    private lateinit var classAdapter: HariAdapter
    private lateinit var classList: ArrayList<ListHasilPencarian>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHasilPencarianBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        classList = ArrayList()
        classList.add(ListHasilPencarian("Senin","00/00/00"))
        classList.add(ListHasilPencarian("Selasa","00/00/00"))
        classList.add(ListHasilPencarian("Rabu","00/00/00"))
        classList.add(ListHasilPencarian("Kamis","00/00/00"))
        classList.add(ListHasilPencarian("Jumat","00/00/00"))
        classList.add(ListHasilPencarian("Sabtu","00/00/00"))
        classList.add(ListHasilPencarian("Minggu","00/00/00"))

        val selected = 0

        binding.rvHari.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
            classAdapter = HariAdapter (classList,selected)
            adapter = classAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_hasilPencarianFragment_to_homeFragment)
        }


    }
}