package com.finalproject.tiketku.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.model.ListHasilPencarian

class HariAdapter(private val listClass:List<ListHasilPencarian>):
    RecyclerView.Adapter<HariAdapter.ViewHolder>() {

    private var selectedCard = -1

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hari = itemView.findViewById<TextView>(R.id.tv_hari)!!
        var tgl = itemView.findViewById<TextView>(R.id.tv_tgl)!!
        val lin1 = itemView.findViewById<View>(R.id.layout_hari)!!

        init {
            lin1.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedCard = position
                    notifyDataSetChanged()

/*                    onItemClickCallback?.onItemClicked(position, listClass[position])*/
                }
            }
        }
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
            holder.lin1.setBackgroundResource(R.drawable.curved_set_class)
            holder.hari.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.tgl.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        } else {
            holder.lin1.setBackgroundResource(R.drawable.curve_set_class)
            holder.hari.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
            holder.tgl.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }

    }

    override fun getItemCount(): Int = listClass.size



    interface OnItemClickCallback {
        fun onItemClicked(position: Int, data:ListHasilPencarian)
    }
}