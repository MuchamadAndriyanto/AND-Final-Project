package com.finalproject.tiketku.view.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.finalproject.tiketku.databinding.FragmentAkunBinding
import com.finalproject.tiketku.model.UpdateProfilePost
import com.finalproject.tiketku.viewmodel.UsersViewModel

class AkunFragment : Fragment() {
    private lateinit var binding: FragmentAkunBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)

        binding.btnUpdate.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val namaLengkap = binding.editTextNamaLengkap.text.toString()
            val alamat = binding.editTextAlamat.text.toString()

            val updateProfile = UpdateProfilePost(username, namaLengkap, alamat)
//            updateProfile(updateProfile)
        }
    }

//    private fun updateProfile(updateProfile: UpdateProfilePost) {
//        viewModel.updateData(updateProfile)
//        viewModel.dataUpdate.observe(viewLifecycleOwner) { response ->
//            if (response != null) {
//                Toast.makeText(requireContext(), "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}
