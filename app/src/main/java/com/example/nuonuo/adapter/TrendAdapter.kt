package com.example.nuonuo.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nuonuo.R
import com.example.nuonuo.bean.TrendListResponse


class TrendAdapter(var beans: List<TrendListResponse.Data>, var context: Context): RecyclerView.Adapter<TrendAdapter.ViewHolder>() {



    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        var nameText: TextView
        var contentText: TextView
        var timeText: TextView
        var headImg: ImageView
        var imgsRL: RecyclerView
        var leaveRL: RecyclerView
        var thumbNum: TextView
        var shareNum: TextView
        var commentNum: TextView
        init {
            this.nameText = view.findViewById(R.id.name_text)
            this.contentText = view.findViewById(R.id.content_text)
            this.timeText = view.findViewById(R.id.time_text)
            this.headImg = view.findViewById(R.id.head_img)
            this.imgsRL = view.findViewById(R.id.imgs_rl)
            this.leaveRL = view.findViewById(R.id.leave_rl)
            this.thumbNum = view.findViewById(R.id.thumb_num)
            this.shareNum = view.findViewById(R.id.share_num)
            this.commentNum = view.findViewById(R.id.comment_num)
            imgsRL.layoutManager = GridLayoutManager(context,2)
            leaveRL.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans[position]
        holder.nameText.text =  bean.name
        holder.contentText.text = bean.content
        holder.timeText.text = bean.stateTime
        Glide.with(context)
            .load(R.drawable.test_bg)
            .into(holder.headImg)
        holder.thumbNum.text = bean.id.toString()
        holder.shareNum.text = bean.id.toString()
        holder.commentNum.text = bean.id.toString()

//        bean.otherPicIds?.apply {
//            holder.imgsRL.adapter = ImgsAdapter(this,context)
//
//        }
//        bean.leaves?.apply {
//            holder.leaveRL.adapter = LeaveAdapter(this,context)
//        }
    }

    override fun getItemCount(): Int {
        return beans.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_trend, parent, false)
        return ViewHolder(view)
    }


}