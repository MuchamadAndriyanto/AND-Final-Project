package com.finalproject.tiketku.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.model.ListHasilPencarian
import com.finalproject.tiketku.model.roundtrip.DataRoundTrip

class JadwalTanggalRoundtripAdapter (private val context: Context, private val list: List<DataRoundTrip>) : RecyclerView.Adapter<JadwalTanggalRoundtripAdapter.ViewHolder>() {

    private var selectedCard = -1

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hari = itemView.findViewById<TextView>(R.id.tv_hari)!!
        var tgl = itemView.findViewById<TextView>(R.id.tv_tgl)!!
        val lin1 = itemView.findViewById<View>(R.id.layout_hari)!!

        init {
            lin1.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedCard = position
                    notifyDataSetChanged()

                    /*                    onItemClickCallback?.onItemClicked(position, listClass[position])*/
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalTanggalRoundtripAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hari, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JadwalTanggalRoundtripAdapter.ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.hari.text = currentItem.hari
        holder.tgl.text = currentItem.tanggalBerangkat

        if (position == selectedCard) {

            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("jadwal", currentItem.tanggalBerangkat)
            editor.apply()

            holder.lin1.setBackgroundResource(R.drawable.curved_set_class)
            holder.hari.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.tgl.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        } else {
            holder.lin1.setBackgroundResource(R.drawable.curve_set_class)
            holder.hari.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
            holder.tgl.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }



    }

    override fun getItemCount(): Int = list.size


    interface OnItemClickCallback {
        fun onItemClicked(position: Int, data: ListHasilPencarian)
    }
}