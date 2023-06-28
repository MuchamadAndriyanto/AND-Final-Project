package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemRiwayatBinding
import com.finalproject.tiketku.model.riwayat.Data
import com.finalproject.tiketku.view.Beranda.HomeFragmentDirections
import com.finalproject.tiketku.view.HistoryFragmentDirections

class RiwayatAdapter(private val context: Context, var list: List<Data>) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    inner class ViewHolder(val binding: ItemRiwayatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRiwayatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        if (item.id != null) {
            holder.binding.status.text = item.id.status_pembayaran

            holder.binding.tvjakarta.text = item.tiket.bandaraAwal.kota
            holder.binding.tvBerangkat.text = item.tiket.tanggal_berangkat
            holder.binding.tvJamBerangkat.text = item.tiket.jam_berangkat

            holder.binding.tvJam.text = item.tiket.selisih_jam.toString() + "h"
            holder.binding.tvMenit.text = item.tiket.selisih_menit.toString() + "m"

            holder.binding.tvMelbourne.text = item.tiket.bandaraTujuan.kota
            holder.binding.tvKedatangan.text = item.tiket.tanggal_kedatangan
            holder.binding.tvJamKedatangan.text = item.tiket.jam_kedatangan

            holder.binding.Kode.text = item.id.kode_booking
            holder.binding.tvPrice.text = item.totalHargaTiket

            holder.binding.cardDetail.setOnClickListener { view ->
                val id = item.id.id
                val action = HistoryFragmentDirections.actionHistoryFragmentToDetailHistoryFragment(id)
                view.findNavController().navigate(action)
            }

            // Set text and background color based on payment status
            if (item.id.status_pembayaran == "paid") {
                holder.binding.status.text = "Paid"
                holder.binding.status.setBackgroundResource(R.drawable.badge_shape_paid)
            } else {
                holder.binding.status.text = "Unpaid"
                holder.binding.status.setBackgroundResource(R.drawable.badge_shape_unpaid)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}