package com.example.nuonuo.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.nuonuo.R
import com.example.nuonuo.bean.TrendBean


class LeaveAdapter(var beans: List<TrendBean.leave>, var context: Context): RecyclerView.Adapter<LeaveAdapter.ViewHolder>() {



    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        var nameText: TextView
        var contentText: TextView
        init {
            this.nameText = view.findViewById(R.id.name_text)
            this.contentText = view.findViewById(R.id.content_text)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans[position]
        holder.nameText.text =  bean.leaveName + "："
        holder.contentText.text = bean.leaveContent
    }

    override fun getItemCount(): Int {
        return beans.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_leave, parent, false)
        return ViewHolder(view)
    }




}