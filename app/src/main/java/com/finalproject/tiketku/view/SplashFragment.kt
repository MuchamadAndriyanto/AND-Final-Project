package com.finalproject.tiketku.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isConnected = isNetworkConnected()
        Handler().postDelayed({

            if (isConnected) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment)

                // Tindakan yang ingin Anda lakukan jika tidak ada koneksi internet

            } else {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment)
            }

        }, 3000)
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}