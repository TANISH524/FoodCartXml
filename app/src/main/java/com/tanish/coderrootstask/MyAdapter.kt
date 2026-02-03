package com.tanish.recyclerviewpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tanish.coderrootstask.ModelClass
import com.tanish.coderrootstask.MenuData
import com.tanish.coderrootstask.databinding.TextshowBinding

class MyAdapter(
    private val list: ArrayList<ModelClass>,
    private val listener: MenuData
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: TextshowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TextshowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.textview.text = model.itemName
        holder.binding.priceText.text = "₹ ${model.itemPrice}"

        holder.binding.btnDelete.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
            listener.price(list.sumOf { it.itemPrice })
        }

        holder.binding.root.setOnClickListener {
            listener.item(position)
        }
    }

    override fun getItemCount(): Int = list.size
}
