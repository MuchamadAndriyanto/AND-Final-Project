@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused"
)

package com.finalproject.tiketku.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.databinding.ItemDestinasiFavBinding
import com.finalproject.tiketku.model.favorit.DataFavorite
import com.bumptech.glide.Glide
import com.finalproject.tiketku.view.Beranda.HomeFragmentDirections

@Suppress("unused")
class DestinasiFavoritAdapter(private val context: Context, private val list: List<DataFavorite>) : RecyclerView.Adapter<DestinasiFavoritAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    inner class ViewHolder(val binding: ItemDestinasiFavBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDestinasiFavBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinasiFavoritAdapter.ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvDestination.text = item.bandaraTujuan.kota
        holder.binding.tvDeparture.text = item.bandaraAwal.kota
        holder.binding.tvTanggalFavorit.text = item.tanggalBerangkat
        holder.binding.tvHargaDestination.text = item.maskapai.hargaTiket
        holder.binding.tvMaskapai.text = item.maskapai.namaMaskapai

        Glide.with(context)
            .load(item.foto)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.binding.ivDestinationFavorit)

        //membawa id ke detail
        holder.binding.cardDetail.setOnClickListener { view ->
            val id = item.id
            val action = HomeFragmentDirections.actionHomeFragmentToDetailPenerbanganFragment(id)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
