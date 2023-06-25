package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.databinding.ItemHariBinding
import com.finalproject.tiketku.databinding.ItemNotifBinding
import com.finalproject.tiketku.model.notif.DataNotif
import com.finalproject.tiketku.model.rountrip.DataRountip
import java.util.Locale

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
        val sortedList = list.sortedBy { it.tanggalBerangkat }
        val item = sortedList[position]
        holder.binding.tvTgl.text = item.tanggalBerangkat


        holder.binding.tvHari.text = "Senin"

        holder.binding.layoutHari.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("jadwal", item.tanggalBerangkat)
            editor.apply()
            Toast.makeText(context, "Jadwal tersimpan", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }
}