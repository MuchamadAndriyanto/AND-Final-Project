package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemDestinasiFavBinding
import com.finalproject.tiketku.databinding.ItemTiketBinding
import com.finalproject.tiketku.model.favorit.DataFavorite

class HasilPenerbanganAdapter(private val context: Context, private val list: List<DataFavorite>)
: RecyclerView.Adapter<HasilPenerbanganAdapter.ViewHolder>() {


    private val sharedPreferencesTo: SharedPreferences = context.getSharedPreferences("MyPrefsTo", Context.MODE_PRIVATE)
    val selectedDestinationTo = sharedPreferencesTo.getString("keyTo", "")

    val filteredList = list.filter { item ->
        item.bandaraAwal.kota == selectedDestinationTo
    }

    val sortedList = (filteredList + (list - filteredList)).toList()


    inner class ViewHolder(val binding: ItemTiketBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTiketBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HasilPenerbanganAdapter.ViewHolder, position: Int) {
        val item = list[position]
        val item2 = sortedList[position]
        holder.binding.tvKota.text = item2.bandaraAwal.kota

        /*holder.binding.tvKota.text = selectedDestinationTo*/

        if (item2.bandaraAwal.kota == selectedDestinationTo) {
            holder.binding.root.visibility = View.VISIBLE
        } else {
            holder.binding.root.visibility = View.GONE

        }

    }



    override fun getItemCount(): Int {
        return sortedList.size
    }
}
