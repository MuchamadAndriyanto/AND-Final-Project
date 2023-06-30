package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemRiwayatBinding
import com.finalproject.tiketku.model.riwayat.Data
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

        holder.binding.cardDetail.setOnClickListener { view ->
            val orderId = item.order.id
            val action = HistoryFragmentDirections.actionHistoryFragmentToDetailHistoryFragment(orderId)
            view.findNavController().navigate(action)
        }

        holder.binding.status.text = item.order.status_pembayaran

            holder.binding.tvjakarta.text = item.tiket.bandaraAwal.kota
            holder.binding.tvBerangkat.text = item.tiket.tanggal_berangkat
            holder.binding.tvJamBerangkat.text = item.tiket.jam_berangkat

            holder.binding.tvJam.text = item.tiket.selisih_jam.toString() + "h"
            holder.binding.tvMenit.text = item.tiket.selisih_menit.toString() + "m"

            holder.binding.tvMelbourne.text = item.tiket.bandaraTujuan.kota
            holder.binding.tvKedatangan.text = item.tiket.tanggal_kedatangan
            holder.binding.tvJamKedatangan.text = item.tiket.jam_kedatangan

            holder.binding.Kode.text = item.order.kode_booking
            holder.binding.tvPrice.text = item.totalHargaTiket

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