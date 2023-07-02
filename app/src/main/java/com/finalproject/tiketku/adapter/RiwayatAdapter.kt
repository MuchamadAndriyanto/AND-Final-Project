package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemRiwayatBinding
import com.finalproject.tiketku.databinding.ItemRiwayatRountripBinding
import com.finalproject.tiketku.model.riwayatRT.Data
import com.finalproject.tiketku.view.HistoryFragmentDirections

class RiwayatAdapter(private val context: Context, var list: List<Data>) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    inner class ViewHolder(val binding: ItemRiwayatRountripBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRiwayatRountripBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.cardDetail.setOnClickListener { view ->
            val orderId = item.order.id
            val action = if (item.tiketPulang == null) {
                HistoryFragmentDirections.actionHistoryFragmentToDetailHistoryFragment(orderId)
            } else {
                HistoryFragmentDirections.actionHistoryFragmentToDetailHistoryRountripFragment(orderId)
            }
            view.findNavController().navigate(action)
        }


        holder.binding.status.text = item.order.status_pembayaran

        holder.binding.apply {
            if (item.tiketPulang == null) {
                // Hanya tampilkan card view untuk tiket berangkat
                tvjakarta.text = item.tiketBerangkat.bandaraAwal.kota
                tvBerangkat.text = item.tiketBerangkat.tanggal_berangkat
                tvJamBerangkat.text = item.tiketBerangkat.jam_berangkat

                tvJam.text = item.tiketBerangkat.selisih_jam.toString() + "h"
                tvMenit.text = item.tiketBerangkat.selisih_menit.toString() + "m"

                tvMelbourne.text = item.tiketBerangkat.bandaraTujuan.kota
                tvKedatangan.text = item.tiketBerangkat.tanggal_kedatangan
                tvJamKedatangan.text = item.tiketBerangkat.jam_kedatangan

                Kode.text = item.order.kode_booking
                tvPrice.text = item.totalHargaTiketBerangkat

                // Sembunyikan card view untuk tiket pulang
                cardview2.visibility = View.GONE
            } else {
                // Tampilkan card view untuk tiket berangkat
                tvjakarta.text = item.tiketBerangkat.bandaraAwal.kota
                tvBerangkat.text = item.tiketBerangkat.tanggal_berangkat
                tvJamBerangkat.text = item.tiketBerangkat.jam_berangkat

                tvJam.text = item.tiketBerangkat.selisih_jam.toString() + "h"
                tvMenit.text = item.tiketBerangkat.selisih_menit.toString() + "m"

                tvMelbourne.text = item.tiketBerangkat.bandaraTujuan.kota
                tvKedatangan.text = item.tiketBerangkat.tanggal_kedatangan
                tvJamKedatangan.text = item.tiketBerangkat.jam_kedatangan

                Kode.text = item.order.kode_booking
                tvPrice.text = item.totalHargaTiketBerangkat

                // Tampilkan card view untuk tiket pulang
                tvjakarta2.text = item.tiketPulang.bandaraAwal?.kota
                tvBerangkat2.text = item.tiketPulang.tanggal_berangkat
                tvJamBerangkat2.text = item.tiketPulang.jam_berangkat

                tvJam2.text = "(" + item.tiketPulang.selisih_jam + "h - "
                tvMenit2.text = item.tiketPulang.selisih_menit.toString() + "m)"

                tvMelbourne2.text = item.tiketPulang.bandaraTujuan?.kota
                tvKedatangan2.text = item.tiketPulang.tanggal_kedatangan
                tvJamKedatangan2.text = item.tiketPulang.jam_kedatangan

                tvBooking2.text = item.order.kode_booking
                tvTotalHarga2.text = item.totalHargaTiketPulang

                // Tampilkan card view untuk tiket pulang
                cardview2.visibility = View.VISIBLE
            }
        }

        // Set text and background color based on payment status
            holder.binding.status.text = if (item.status) "Paid" else "Unpaid"
            if (item.order.status_pembayaran == "paid") {
                holder.binding.status.text = "Paid"
                holder.binding.status.setBackgroundResource(R.drawable.badge_shape_paid)
            } else {
                holder.binding.status.text = "Unpaid"
                holder.binding.status.setBackgroundResource(R.drawable.badge_shape_unpaid)
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}