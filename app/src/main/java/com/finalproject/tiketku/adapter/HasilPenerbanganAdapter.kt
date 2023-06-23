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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemDestinasiFavBinding
import com.finalproject.tiketku.databinding.ItemTiketBinding
import com.finalproject.tiketku.model.caripenerbangan.DataCariPenerbangan
import com.finalproject.tiketku.model.favorit.DataFavorite
import com.finalproject.tiketku.view.HasilPencarianFragmentDirections

class HasilPenerbanganAdapter(private val context: Context, private var list: List<DataCariPenerbangan>)
    : RecyclerView.Adapter<HasilPenerbanganAdapter.ViewHolder>() {

    private val sharedPreferencesTo: SharedPreferences = context.getSharedPreferences("MyPrefsTo", Context.MODE_PRIVATE)
    val selectedDestinationTo = sharedPreferencesTo.getString("keyTo", "")

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val selectedDestination = sharedPreferences.getString("key", "")

    private val SetClasSharedPref: SharedPreferences = context.getSharedPreferences("selected_class", Context.MODE_PRIVATE)
    val setClassSharePrefe = SetClasSharedPref.getString("selected_class", "")
    val selected_class = setClassSharePrefe

    private val filterPref: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val filterSharedPref = filterPref.getString("filter_key", "")
    val filter = filterSharedPref



    /*    val filteredList = list.filter { item ->
            item.bandaraAwal.kota == selectedDestination && item.bandaraTujuan.kota == selectedDestinationTo
        }.toMutableList()*/

    private val filteredList = mutableListOf<DataCariPenerbangan>()

    init {
        updateFilteredList()
    }

    inner class ViewHolder(val binding: ItemTiketBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTiketBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item2 = filteredList[position]
        holder.binding.tvKota.text = item2.bandaraAwal.kota
        holder.binding.tvKota2.text = item2.bandaraTujuan.kota
        holder.binding.tvJam.text = item2.jamBerangkat
        holder.binding.tvJam2.text = item2.jamKedatangan
        holder.binding.tvJa.text = item2.selisihJam.toString()
        holder.binding.tvHarga.text = item2.maskapai.hargaTiket
        holder.binding.maskapai1.text = item2.maskapai.namaMaskapai
        holder.binding.setClass.text = selected_class



        if (item2.bandaraAwal.kota == selectedDestination && item2.bandaraTujuan.kota == selectedDestinationTo) {
            holder.binding.root.visibility = View.VISIBLE
        } else {
            holder.binding.root.visibility = View.GONE
            val indexToRemove = list.indexOf(item2)
            if (indexToRemove >= 0) {
                filteredList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, filteredList.size)
            }
        }

        //kedetail
        holder.binding.cardDetail.setOnClickListener { view ->
            val id = item2.id
            val action = HasilPencarianFragmentDirections.actionHasilPencarianFragmentToDetailPenerbanganFragment(id)
            holder.itemView.findNavController().navigate(action)
        }



    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    private fun removeItemAt(list: MutableList<DataCariPenerbangan>, index: Int) {
        if (index >= 0 && index < list.size) {
            list.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    private fun updateFilteredList() {
        filteredList.clear()
        filteredList.addAll(list.filter { item ->
            item.bandaraAwal.kota == selectedDestination && item.bandaraTujuan.kota == selectedDestinationTo
        })

        if (filter == "Termurah") {
            filteredList.sortBy { it.maskapai.hargaTiketNoFormat }
        }else if (filter == "Terpendek") {
            filteredList.sortBy { it.selisihJam }
        }else if (filter+"b"
            == "Paling Akhirb") {
            filteredList.sortByDescending { it.jamBerangkat }
        }else if (filter+"b" == "Paling Awalb") {
            filteredList.sortBy { it.jamBerangkat }
        }else if (filter+"d"
            == "Paling Akhird") {
            filteredList.sortByDescending { it.jamKedatangan }
        }else if (filter+"d" == "Paling Awald") {
            filteredList.sortBy { it.jamKedatangan }
        }

        notifyDataSetChanged()
    }


/*    fun setFilterPreferences(filter: String) {
        val editor = filterPref.edit()
        editor.putString("filter_key", filter)
        editor.apply()

        updateFilteredList()
    }

    fun setDestinations(destination: String, destinationTo: String) {
        val editor = sharedPreferences.edit()
        editor.putString("key", destination)
        editor.apply()

        val editorTo = sharedPreferencesTo.edit()
        editorTo.putString("keyTo", destinationTo)
        editorTo.apply()

        updateFilteredList()
    }*/
}


