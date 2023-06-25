package com.finalproject.tiketku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.databinding.ItemRiwayatBinding
import com.finalproject.tiketku.model.riwayat.Data

class RiwayatAdapter(private val context: Context, private val list: List<Data>) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRiwayatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRiwayatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.status.text = item.id.status_pembayaran
        holder.binding.tvjakarta.text = item.tiket.bandaraAwal.kota
        holder.binding.tvBerangkat.text = item.tiket.tanggal_berangkat
        holder.binding.tvJamBerangkat.text = item.tiket.jam_berangkat

        holder.binding.tvMelbourne.text = item.tiket.bandaraTujuan.kota
        holder.binding.tvKedatangan.text = item.tiket.tanggal_kedatangan
        holder.binding.tvJamKedatangan.text = item.tiket.jam_kedatangan


        holder.binding.Kode.text = item.id.kode_booking
        holder.binding.tvPrice.text = item.totalHargaTiket
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
