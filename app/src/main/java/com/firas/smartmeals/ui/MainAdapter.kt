package com.firas.smartmeals.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firas.smartmeals.R
import com.firas.smartmeals.data.model.Category

class MainAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<Category> = ArrayList()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img: ImageView = itemView.findViewById(R.id.imgview)
        var tvCat: TextView = itemView.findViewById(R.id.category)
        var tvDescription: TextView = itemView.findViewById(R.id.description)

        fun bind(category: Category) {
            Glide
                .with(itemView)
                .load(category.strCategoryThumb)
                .centerInside()
                .into(img)

            tvCat.text = category.strCategory
            tvDescription.text = category.strCategoryDescription


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind(list[position])
    }

    fun setData(setList: List<Category>) {
        list = setList
        notifyDataSetChanged()
    }
}