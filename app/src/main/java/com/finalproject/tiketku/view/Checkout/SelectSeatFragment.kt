package com.finalproject.tiketku.view.Checkout

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.FragmentSelectSeatBinding
import com.finalproject.tiketku.model.order.DataOrder
import com.finalproject.tiketku.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSeatFragment : Fragment() {
    private lateinit var binding: FragmentSelectSeatBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var viewModel: OrderViewModel
    private var selectedSeat: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectSeatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val email = sharedPref.getString("email", "")
        val idPenerbangan = requireArguments().getInt("idPenerbangan", 0)
        val jumlahPenumpang = sharedPref.getString("jumlahPenumpang", "")
        val namaKeluarga = sharedPref.getString("namaKeluarga", "")
        val namaLengkap = sharedPref.getString("namaLengkap", "")
        val noTelp = sharedPref.getString("noTelp", "")

        Log.d("SelectSeatCheck", "data : $email, $idPenerbangan, $jumlahPenumpang, $namaKeluarga, $namaLengkap, $noTelp")

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_selectSeatFragment_to_checkoutBiodataPemesanFragment)
        }
        binding.btnSubmit.setOnClickListener {
            // Mendapatkan nilai-nilai dari SharedPreferences
            val email = sharedPref.getString("email", "")
            val idPenerbangan = requireArguments().getInt("idPenerbangan", 0)
            val jumlahPenumpang = sharedPref.getString("jumlahPenumpang", "")
            val namaKeluarga = sharedPref.getString("namaKeluarga", "")
            val namaLengkap = sharedPref.getString("namaLengkap", "")
            val noTelp = sharedPref.getString("noTelp", "")

// Memastikan kursi dipilih
            if (selectedSeat != null) {
                // Misalkan Anda memiliki fungsi untuk melakukan proses pemesanan atau tindakan lainnya
                if (email != null && jumlahPenumpang != null && namaKeluarga != null && namaLengkap != null && noTelp != null) {
                    processBooking(email, idPenerbangan, jumlahPenumpang, selectedSeat!!, namaKeluarga!!, namaLengkap!!, noTelp)
                    Log.d("SelectSeatCheck", "data : $selectedSeat, $email, $idPenerbangan,$jumlahPenumpang, $namaKeluarga, $namaLengkap,$noTelp")

                    // Navigasi ke halaman selanjutnya setelah melakukan proses
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    bundle.putInt("idPenerbangan", idPenerbangan)
                    bundle.putString("jumlahPenumpang", jumlahPenumpang)
                    bundle.putString("namaKeluarga", namaKeluarga)
                    bundle.putString("namaLengkap", namaLengkap)
                    bundle.putString("noTelp", noTelp)
                    findNavController().navigate(R.id.action_selectSeatFragment_to_checkOutDetailFragment, bundle)
                } else {
                    // Salah satu nilai-nilai null, lakukan sesuatu
                    Toast.makeText(requireContext(), "Gagal pesan", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Kursi tidak dipilih, lakukan sesuatu
                Toast.makeText(requireContext(), "Pilih kursi terlebih dahulu", Toast.LENGTH_SHORT).show()
            }

        }

        /// Mengatur onClickListener untuk tombol kursi
        val seatButtons = binding.seatGrid.children.toList()

        for (button in seatButtons) {
            button.setOnClickListener {
                // Mengambil ID tombol yang diklik
                val seatId = button.id

                // Mengambil nilai kursi dari ID tombol
                val seat = resources.getResourceEntryName(seatId)

                // Mengatur kursi terpilih
                setSelectedSeat(seat)

                // Mengubah tampilan tombol saat dipilih
                updateButtonAppearance(seatId)
            }
        }
    }

    // Fungsi untuk mengatur kursi terpilih
    private fun setSelectedSeat(seat: String) {
        if (selectedSeat == seat) {
            // Menghapus pilihan jika kursi sudah dipilih sebelumnya
            selectedSeat = null
        } else {
            // Mengatur kursi terpilih
            selectedSeat = seat
        }
    }

    // Fungsi untuk memperbarui tampilan tombol berdasarkan status pemilihan kursi
    private fun updateButtonAppearance(selectedSeatId: Int) {
        val seatButtons = binding.seatGrid.children.toList()

        for (button in seatButtons) {
            if (button.id == selectedSeatId) {
                // Mengatur tampilan tombol saat dipilih
                button.isSelected = true
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkblue_03))
            } else {
                // Mengatur tampilan tombol saat tidak dipilih
                button.isSelected = false
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
            }
        }
    }

    // Fungsi untuk melakukan proses pemesanan
    private fun processBooking(
        email: String,
        idPenerbangan: Int,
        jumlahPenumpang: String,
        kursi: String,
        namaKeluarga: String,
        namaLengkap: String,
        noTelp: String
    ) {
        // Misalkan di sini Anda mengirim data pesanan ke server atau melakukan tindakan lainnya
        // Anda dapat menggunakan ViewModel untuk mengirimkan data ke server atau melakukan operasi lainnya
        val token = sharedPref.getString("accessToken", "")
        val dataOrder = DataOrder(
            0,
            email,
            idPenerbangan,
            jumlahPenumpang.toInt(),
            kursi,
            namaKeluarga,
            namaLengkap,
            noTelp)

        if (!token.isNullOrEmpty()) {
            viewModel.postOrders(token, dataOrder)
        } else {
            // Data detail tidak tersedia, lakukan sesuatu
            Toast.makeText(requireContext(), "Gagal pesan", Toast.LENGTH_SHORT).show()
        }
    }
}
