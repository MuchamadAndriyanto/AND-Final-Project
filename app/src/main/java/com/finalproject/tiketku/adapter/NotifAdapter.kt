package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.databinding.ItemNotifBinding
import com.finalproject.tiketku.model.notif.DataNotif

class NotifAdapter(private val context: Context, private val list: List<DataNotif>) : RecyclerView.Adapter<NotifAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    inner class ViewHolder(val binding: ItemNotifBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotifBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvIsi.text = item.pesan
        holder.binding.tvJudul.text = item.judul
        holder.binding.tvTgl.text = item.tanggal
        holder.binding.tvJam.text = item.jam
        holder.binding.tvSyarat.text = item.pesanTambahan

    }

    override fun getItemCount(): Int {
        return list.size
    }
}