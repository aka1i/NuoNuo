package com.example.nuonuo.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.nuonuo.R
import com.example.nuonuo.bean.MessageItemBean


class MessageAdapter(var beans: List<MessageItemBean>,var context: Context): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {



    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        var nameText: TextView
        var contentText: TextView
        var timeText: TextView
        var headImg: ImageView
        init {
            this.nameText = view.findViewById(R.id.name_text)
            this.contentText = view.findViewById(R.id.content_text)
            this.timeText = view.findViewById(R.id.time_text)
            this.headImg = view.findViewById(R.id.message_item_head)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans[position]
        holder.nameText.text =  bean.name
        holder.contentText.text = bean.content
        holder.timeText.text = bean.time
        holder.headImg.background = context.getDrawable(R.drawable.head_img)
    }

    override fun getItemCount(): Int {
        return beans.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }




}