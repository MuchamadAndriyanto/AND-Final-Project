package com.finalproject.tiketku.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemDestinasiBinding
import com.finalproject.tiketku.model.BandaraAwal
import com.finalproject.tiketku.model.search.Data
import com.finalproject.tiketku.model.search.SearchResponse

class DestinasiAdapter (var list : List<Data>): RecyclerView.Adapter<DestinasiAdapter.ViewHolder>() {
    var onClick : ((Data) -> Unit)? = null
    class ViewHolder(var binding : ItemDestinasiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindFilms(item: Data){
            binding.tvDestination.setOnClickListener{
                val bundle = Bundle()
                Navigation.findNavController(itemView).navigate(R.id.action_destinasiPencarianFragment_to_homeFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemDestinasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var source = list[position]
        holder.bindFilms(list[position])
        holder.binding.tvDestination.text = source.kota

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            Navigation.findNavController(it).navigate(R.id.action_destinasiPencarianFragment_to_homeFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}