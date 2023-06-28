package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemHariBinding
import com.finalproject.tiketku.model.onetrip.DataOneTrip
import com.finalproject.tiketku.model.roundtrip.DataRoundTrip
import com.finalproject.tiketku.model.rountrip.DataRountip

class JadwalTanggalRoundtripAdapter (private val context: Context, private val list: List<DataRoundTrip>) : RecyclerView.Adapter<JadwalTanggalRoundtripAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private var selectedCard = -1

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemHariBinding) : RecyclerView.ViewHolder(binding.root) {
        var hari = itemView.findViewById<TextView>(R.id.tv_hari)
        var tgl = itemView.findViewById<TextView>(R.id.tv_tgl)
        val lin1 = itemView.findViewById<View>(R.id.layout_hari)

        init {
            lin1.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedCard = position
                    notifyDataSetChanged()

                    onItemClickCallback?.onItemClicked(position, list[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHariBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JadwalTanggalRoundtripAdapter.ViewHolder, position: Int) {
        val sortedList = list.sortedBy { it.tanggalBerangkat }
        val item = sortedList[position]
        holder.binding.tvTgl.text = item.tanggalBerangkat
        holder.binding.tvHari.text = item.hari

        if (position == selectedCard) {
            holder.lin1.setBackgroundResource(R.drawable.curved_set_class)
            holder.hari.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.tgl.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            holder.lin1.setBackgroundResource(R.drawable.curve_set_class)
            holder.hari.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.tgl.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }



        holder.binding.layoutHari.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("jadwal", item.tanggalBerangkat)
            editor.apply()
            Toast.makeText(context, "Jadwal tersimpan", Toast.LENGTH_SHORT).show()

        }
    }


    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(position: Int, data: DataRoundTrip)
    }
}