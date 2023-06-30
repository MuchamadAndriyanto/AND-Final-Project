package com.finalproject.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.model.DummySetClass

class SetClassAdapter(private val listClass: List<DummySetClass>) :

    RecyclerView.Adapter<SetClassAdapter.ViewHolder>() {

    private var selectedCard = -1
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val className: TextView = itemView.findViewById(R.id.tv_Class)

        val layoutSetClass: View = itemView.findViewById(R.id.layout_SetKelas)
        val succesIcon: View = itemView.findViewById(R.id.iv_Succes)

        init {
            layoutSetClass.setOnClickListener {
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
            .inflate(R.layout.item_set_class, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listClass[position]
        holder.className.text = currentItem.kelas

        if (position == selectedCard) {
            holder.layoutSetClass.setBackgroundResource(R.drawable.curved_set_class)
            holder.succesIcon.visibility = View.VISIBLE
            holder.className.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )


        } else {
            holder.layoutSetClass.setBackgroundResource(R.drawable.curve_set_class)
            holder.succesIcon.visibility = View.GONE
            holder.className.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )

        }
    }

    override fun getItemCount(): Int = listClass.size

    interface OnItemClickCallback {
        fun onItemClicked(position: Int, data: DummySetClass)
    }
}
