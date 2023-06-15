package com.finalproject.tiketku.view.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentAkunBinding
import com.finalproject.tiketku.viewmodel.UsersViewModel


class AkunFragment : Fragment() {

    private lateinit var binding: FragmentAkunBinding
    private lateinit var akunVM: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        akunVM = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding.btnUpdate.setOnClickListener {
            updateAkun()
        }
        observeAkunData()
    }

    private fun observeAkunData() {
        akunVM.updateData.observe(viewLifecycleOwner) { responseAkunItems ->
            if (responseAkunItems != null && responseAkunItems.isNotEmpty()) {
                Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
                // Lakukan tindakan setelah berhasil memperbarui data akun
            } else {
                Toast.makeText(requireContext(), "Update Failed", Toast.LENGTH_SHORT).show()
                // Lakukan tindakan jika gagal memperbarui data akun
            }
        }
    }

    private fun updateAkun() {
        val id = 0
        val username = binding.editTextUsername.text.toString()
        val alamat = binding.editTextAlamat.text.toString()
        val namaLengkap = binding.editTextNamaLengkap.text.toString()

        if (username.isEmpty() || alamat.isEmpty() || namaLengkap.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please fill in all fields",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            akunVM.updateData(id, username, alamat, namaLengkap)
        }
    }
}
