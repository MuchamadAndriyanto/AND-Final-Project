package com.finalproject.tiketku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R

class HistoriPencarianAdapter(private val context: Context, private val data: List<String>) :
RecyclerView.Adapter<HistoriPencarianAdapter.ViewHolder>() {
    var onItemClick: ((String) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchItem: TextView = itemView.findViewById(R.id.tvDestination)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_histori_pencarian, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchItem = data[position]
        holder.searchItem.text = searchItem

        holder.itemView.setOnClickListener {
            // Handle klik item histori pencarian
            onItemClick?.invoke(searchItem)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
