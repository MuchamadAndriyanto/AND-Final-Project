package com.finalproject.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.model.DummySetClass
import com.finalproject.tiketku.model.ListHasilPencarian

class HariAdapter(private val listClass:List<ListHasilPencarian>, val selectedCard:Int):
    RecyclerView.Adapter<HariAdapter.ViewHolder>() {

    var onItemClick: ((ListHasilPencarian) -> Unit)? = null

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hari = itemView.findViewById<TextView>(R.id.tv_hari)
        var tgl = itemView.findViewById<TextView>(R.id.tv_tgl)
        val lin1 = itemView.findViewById<View>(R.id.layout_hari)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HariAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hari, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HariAdapter.ViewHolder, position: Int) {
        val currentItem = listClass[position]
        holder.hari.text = currentItem.hari
        holder.tgl.text = currentItem.tgl

        if (position == selectedCard) {
            holder.lin1.setBackgroundResource(R.drawable.hover2)
        } else {
            holder.lin1.setBackgroundResource(R.drawable.curve_set_class)
            holder.hari.setTextColor(holder.itemView.resources.getColor(R.color.black))
            holder.tgl.setTextColor(holder.itemView.resources.getColor(R.color.black))
        }

        holder.lin1.setOnClickListener {
            onItemClickCallback?.onItemClicked(
                holder.adapterPosition,
                listClass[holder.adapterPosition]
            )
        }

    }

    override fun getItemCount(): Int = listClass.size

    interface OnItemClickCallback {
        fun onItemClicked(position: Int, data:ListHasilPencarian)
    }
}