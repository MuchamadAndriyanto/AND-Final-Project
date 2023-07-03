package com.finalproject.tiketku.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemNotifBinding
import com.finalproject.tiketku.model.notif.DataNotif

class NotifAdapter(
    private val context: Context,
    private val list: List<DataNotif>
) : RecyclerView.Adapter<NotifAdapter.ViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: DataNotif)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(val binding: ItemNotifBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.rootLayout.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = list[position]
                    item.isRead = true
                    notifyDataSetChanged()
                    onItemClickListener?.onItemClick(item)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotifBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvIsi.text = item.pesan
        holder.binding.tvJudul.text = item.judul
        holder.binding.tvTgl.text = item.tanggal
        holder.binding.tvJam.text = item.jam
        holder.binding.tvSyarat.text = item.pesanTambahan

        if (item.isRead) {
            holder.binding.rootLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        } else {
            holder.binding.rootLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
