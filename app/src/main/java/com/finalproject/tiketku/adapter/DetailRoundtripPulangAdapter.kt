package com.finalproject.tiketku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.databinding.ItemDetailRoundtripBinding
import com.finalproject.tiketku.model.detailrountrippergi.DetailRoundtripPergiResponse

class DetailRoundtripPulangAdapter(private val context: Context, private val detailRoundtrip: DetailRoundtripPergiResponse?) : RecyclerView.Adapter<DetailRoundtripPulangAdapter.ViewHolder>() {

    // ViewHolderz
    inner class ViewHolder(val binding: ItemDetailRoundtripBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailRoundtripBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detail = detailRoundtrip
        holder.binding.detailTime.text = detail?.dataPergi?.jamBerangkat
        holder.binding.detailTimeArrived.text = detail?.dataPergi?.jamKedatangan
        holder.binding.detailDate.text = detail?.dataPergi?.tanggalBerangkat
        holder.binding.detailDateArrived.text = detail?.dataPergi?.tanggalBerangkat
        holder.binding.detailAirport.text = detail?.dataPergi?.bandaraAwal?.namaBandara
        holder.binding.detailAirportArrived.text = detail?.dataPergi?.bandaraTujuan?.namaBandara
        holder.binding.Maskapai.text = detail?.dataPergi?.maskapai?.namaMaskapai
        holder.binding.kodeMaskapai.text = detail?.dataPergi?.maskapai?.kode_maskapai
    }

    override fun getItemCount(): Int {
        return 1
    }
}
