package com.finalproject.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.model.ListFilter
import com.finalproject.tiketku.model.ListHasilPencarian

class FilterAdapter(private val listClass:List<ListFilter>):
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var selectedCard = -1
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var text1 = itemView.findViewById<TextView>(R.id.text1)
        var text2 = itemView.findViewById<TextView>(R.id.text2)
        val lin1 = itemView.findViewById<View>(R.id.layout_filter)

        init {
            lin1.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedCard = position
                    notifyDataSetChanged()
                    onItemClickCallback?.onItemClicked(position, listClass[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filter, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listClass[position]
        holder.text1.text = currentItem.text1
        holder.text2.text = currentItem.text2

        if (position == selectedCard) {
            holder.lin1.setBackgroundResource(R.drawable.curved_set_class)
            holder.text1.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.text2.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        } else {
            holder.lin1.setBackgroundResource(R.drawable.curve_set_class)
            holder.text1.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
            holder.text2.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }
    }

    override fun getItemCount(): Int = listClass.size

    interface OnItemClickCallback {
        fun onItemClicked(position: Int, data:ListFilter)
    }
}