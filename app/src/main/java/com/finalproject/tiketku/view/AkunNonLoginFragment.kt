package com.finalproject.tiketku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentAkunNonLoginBinding
import com.finalproject.tiketku.databinding.FragmentHomeBinding
import com.finalproject.tiketku.databinding.FragmentSetClassBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AkunNonLoginFragment : Fragment() {

    private lateinit var binding : FragmentAkunNonLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAkunNonLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_akunNonLoginFragment_to_homeFragment)
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_akunNonLoginFragment_to_loginFragment)
        }

    }
    override fun onResume() {
        super.onResume()
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        enableAllMenuItems(bottomNavigationView.menu)
    }
    private fun enableAllMenuItems(menu: Menu) {
        for (i in 0 until menu.size()) {
            menu.getItem(i).isEnabled = true
        }
    }
}