package com.example.nuonuo.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.nuonuo.R
import com.example.nuonuo.adapter.MessageAdapter
import com.example.nuonuo.bean.MessageLab
import kotlinx.android.synthetic.main.fragment_message_list.*

/**
 * A simple [Fragment] subclass.
 */
class MessageListFragment(private val type: Int) : Fragment() {

    private var myView: View? = null

    private  var messageAdapter: MessageAdapter? = null

    companion object{
        const val TYPE_SEND = 0
        const val TYPE_RECEIVE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_message_list, container, false)
        init()
        return myView
    }


    fun init(){
        activity?.apply {
            val recyclerView = myView?.findViewById<RecyclerView>(R.id.message_rv)
            recyclerView?.layoutManager = LinearLayoutManager(this)
            when(type){
                TYPE_SEND -> {
                    messageAdapter = MessageAdapter(MessageLab.sendMessages,this)
                }
                TYPE_RECEIVE -> {
                    messageAdapter = MessageAdapter(MessageLab.receiveMessage,this)
                }
            }
            recyclerView?.adapter = messageAdapter
        }
    }


    fun refresh(){
        if (isVisible)
        messageAdapter?.notifyDataSetChanged()
    }
}
