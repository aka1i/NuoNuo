package com.example.nuonuo.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.nuonuo.R
import com.example.nuonuo.bean.MessageListResponse


class MessageAdapter(var beans: List<MessageListResponse.MessageItemBean>, var context: Context): RecyclerView.Adapter<MessageAdapter.ViewHolder>(){



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
        holder.nameText.text =  bean.sendName
        holder.contentText.text = bean.content
        holder.timeText.text = bean.stateTime
        Glide.with(context)
            .load(bean.headPicUrl)
            .into(holder.headImg)
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