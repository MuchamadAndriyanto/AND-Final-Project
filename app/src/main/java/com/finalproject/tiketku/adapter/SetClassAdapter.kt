package com.finalproject.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.tiketku.R
import com.finalproject.tiketku.model.DummySetClass

class SetClassAdapter (private val listClass:List<DummySetClass>,val selectedCard:Int):
    RecyclerView.Adapter<SetClassAdapter.ViewHolder>() {

    var onItemClick: ((DummySetClass) -> Unit)? = null

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val className = itemView.findViewById<TextView>(R.id.tv_Class)
        val priceClass = itemView.findViewById<TextView>(R.id.tv_Price)
        val layoutSetClass = itemView.findViewById<View>(R.id.layout_SetKelas)
        val succesIcon = itemView.findViewById<View>(R.id.iv_Succes)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetClassAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_set_class,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SetClassAdapter.ViewHolder, position: Int) {
        val currentItem = listClass[position]
        holder.className.text = currentItem.kelas
        holder.priceClass.text = currentItem.harga

        if (position == selectedCard){
            holder.layoutSetClass.setBackgroundResource(R.drawable.curved_set_class)
            holder.succesIcon.visibility = View.VISIBLE
        } else{
            holder.layoutSetClass.setBackgroundResource(R.drawable.curve_set_class)
            holder.succesIcon.visibility = View.GONE
            holder.className.setTextColor(holder.itemView.resources.getColor(R.color.black))
            holder.priceClass.setTextColor(holder.itemView.resources.getColor(R.color.black))
        }

        holder.layoutSetClass.setOnClickListener {
            onItemClickCallback?.onItemClicked(holder.adapterPosition,listClass[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = listClass.size

    interface OnItemClickCallback{
        fun onItemClicked(position: Int ,data: DummySetClass)
    }
}