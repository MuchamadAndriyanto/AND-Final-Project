package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemDestinasiBinding
import com.finalproject.tiketku.model.search.Data

class DestinasiToAdapter(context: Context, private val list: List<Data>) : RecyclerView.Adapter<DestinasiToAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefsTo", Context.MODE_PRIVATE)

    inner class ViewHolder(val binding: ItemDestinasiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindFilms(item: Data, sharedPreferences: SharedPreferences) {
            binding.tvDestination.setOnClickListener {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("keyTo", item.kota)
                editor.apply()

                val navController = Navigation.findNavController(binding.root)
                navController.previousBackStackEntry?.savedStateHandle?.set("selected_destination_to", item.kota)
                navController.navigate(R.id.action_destinasiPencarianToFragment_to_homeFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDestinasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindFilms(item, sharedPreferences)
        holder.binding.tvDestination.text = item.kota

        holder.itemView.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            // Menyimpan data ke SharedPreferences
            editor.putString("keyTo", item.kota)
            editor.apply()

            Navigation.findNavController(it).navigate(R.id.action_destinasiPencarianToFragment_to_homeFragment)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}