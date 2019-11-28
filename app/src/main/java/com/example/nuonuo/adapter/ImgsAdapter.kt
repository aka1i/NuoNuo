package com.example.nuonuo.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nuonuo.R

class ImgsAdapter(var beans: List<String>, var context: Context): RecyclerView.Adapter<ImgsAdapter.ViewHolder>() {

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        var img: ImageView
        init {
            this.img = view.findViewById(R.id.img_view)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans[position]

        Glide.with(context)
            .load(R.drawable.test_bg)
            .centerCrop()
            .into(holder.img)

    }

    override fun getItemCount(): Int {
        return beans.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_img, parent, false)
        return ViewHolder(view)
    }




}