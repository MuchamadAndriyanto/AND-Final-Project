package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemDestinasiBinding
import com.finalproject.tiketku.model.BandaraAwal
import com.finalproject.tiketku.model.search.Data
import com.finalproject.tiketku.model.search.SearchResponse

class DestinasiAdapter (val context: Context,var list : List<Data>): RecyclerView.Adapter<DestinasiAdapter.ViewHolder>() {
    var onClick : ((Data) -> Unit)? = null

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    class ViewHolder(var binding : ItemDestinasiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindFilms(item: Data,sharedPreferences: SharedPreferences){
            binding.tvDestination.setOnClickListener{
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                // Menyimpan data ke SharedPreferences
                editor.putString("key", item.kota)
                editor.apply()

                Navigation.findNavController(itemView).navigate(R.id.action_destinasiPencarianFragment_to_homeFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemDestinasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var source = list[position]
        holder.bindFilms(source , sharedPreferences)
        holder.binding.tvDestination.text = source.kota

        holder.itemView.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            // Menyimpan data ke SharedPreferences
            editor.putString("key", source.kota)
            editor.apply()

            Navigation.findNavController(it).navigate(R.id.action_destinasiPencarianFragment_to_homeFragment)
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }


}