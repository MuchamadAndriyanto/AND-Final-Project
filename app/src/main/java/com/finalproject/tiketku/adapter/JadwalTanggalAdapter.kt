package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.databinding.ItemHariBinding
import com.finalproject.tiketku.databinding.ItemNotifBinding
import com.finalproject.tiketku.model.notif.DataNotif
import com.finalproject.tiketku.model.rountrip.DataRountip

class JadwalTanggalAdapter (private val context: Context, private val list: List<DataRountip>) : RecyclerView.Adapter<JadwalTanggalAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    inner class ViewHolder(val binding: ItemHariBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHariBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JadwalTanggalAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvTgl.text = item.tanggalBerangkat

    }

    override fun getItemCount(): Int {
        return list.size
    }
}