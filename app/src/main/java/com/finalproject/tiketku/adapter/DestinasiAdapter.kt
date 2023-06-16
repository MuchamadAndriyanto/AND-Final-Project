package com.finalproject.tiketku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.databinding.ItemDestinasiBinding
import com.finalproject.tiketku.model.BandaraAwal
import com.finalproject.tiketku.model.search.Data

class DestinasiAdapter (var list : List<Data>): RecyclerView.Adapter<DestinasiAdapter.ViewHolder>() {
    var onClick : ((Data) -> Unit)? = null
    class ViewHolder(var binding : ItemDestinasiBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemDestinasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var source = list[position]
        holder.binding.tvDestination.text = source.kota

    }

    override fun getItemCount(): Int {
        return  list.size
    }
}