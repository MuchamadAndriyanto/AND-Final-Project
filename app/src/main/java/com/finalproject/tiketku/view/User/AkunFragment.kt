package com.finalproject.tiketku.view.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.tiketku.databinding.FragmentAkunBinding

class AkunFragment : Fragment() {

    private lateinit var binding: FragmentAkunBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

}
