package com.example.nuonuo.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.nuonuo.R
import com.example.nuonuo.bean.MessageListResponse
import com.example.nuonuo.ui.activity.CarOwnerActivity
import com.example.nuonuo.utils.HeadImgUtil
import com.example.nuonuo.utils.PopUpUtil


class MessageAdapter(var beans: List<MessageListResponse.MessageItemBean>, var context: Context,val type:Int): RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    var onItemClickListener: OnItemClickListener = object : OnItemClickListener{
        override fun onclick(position: Int) {
            showPop(beans[position])
        }


        private  fun showPop(messageItemBean: MessageListResponse.MessageItemBean) {
            val bottomView =
                View.inflate(context, R.layout.layout_message_pop, null)
            val nameText = bottomView.findViewById<TextView>(R.id.name_text)
            val contextText = bottomView.findViewById<TextView>(R.id.content_text)
            val timetText = bottomView.findViewById<TextView>(R.id.time_text)

            if (type == 0)
                nameText.text = messageItemBean.getName
            else
                nameText.text = messageItemBean.sendName
            contextText.text = messageItemBean.content + "(点击进入主页)"
            timetText.text = messageItemBean.stateTime

            val pop = PopupWindow(bottomView, -1, -2)
            pop.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            pop.isOutsideTouchable = true
            pop.isFocusable = true


            val window = (context as Activity).window
            window?.run {
                val lp = this.attributes
                lp?.alpha = 0.5f
                window.attributes = lp
                pop.setOnDismissListener {
                    val lp = this.attributes
                    lp.alpha = 1f
                    window.attributes = lp
                }
                pop.animationStyle = R.style.main_menu_photo_anim
                pop.showAtLocation(this.decorView, Gravity.CENTER, 0, 0)
            }


            val clickListener = View.OnClickListener { view ->
                when(context){
                    is Activity -> {
                        //todo 手机号2处
                        if (type == 0)
                        context.startActivity(CarOwnerActivity.newIntent(context,messageItemBean.getId!!,
                            messageItemBean.getName,messageItemBean.headPicUrl,messageItemBean.getPhone ?:""))
                        else
                            context.startActivity(CarOwnerActivity.newIntent(context,messageItemBean.sendId!!,
                                messageItemBean.sendName,messageItemBean.headPicUrl,messageItemBean.sendPhone?:""))
                    }
                }
                PopUpUtil.closePopupWindow(pop)
            }

            bottomView.setOnClickListener(clickListener)
        }
    }

    interface OnItemClickListener{
        fun onclick(position: Int)
    }



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
        if (type == 0)
        {
            holder.nameText.text =  bean.getName
        }else{
            holder.nameText.text =  bean.sendName
        }
        val options = HeadImgUtil.getHeadImgOptions("")
        holder.contentText.text = bean.content
        holder.timeText.text = bean.stateTime
        Glide.with(context)
            .load(bean.headPicUrl)
            .apply(options)
            .into(holder.headImg)
        holder.itemView.setOnClickListener{
            onItemClickListener.onclick(position)
        }
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