package com.example.nuonuo.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.nuonuo.R
import com.example.nuonuo.adapter.MessageAdapter
import com.example.nuonuo.bean.MessageLab
import kotlinx.android.synthetic.main.fragment_message_list.*

/**
 * A simple [Fragment] subclass.
 */
class MessageListFragment(private val type: Int) : Fragment() {

    private lateinit var messageAdapter: MessageAdapter

    companion object{
        const val TYPE_SEND = 0
        const val TYPE_RECEIVE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        activity?.apply {
            message_rv.layoutManager = LinearLayoutManager(this)
            when(type){
                TYPE_SEND -> {
                    messageAdapter = MessageAdapter(MessageLab.sendMessages,this)
                }
                TYPE_RECEIVE -> {
                    messageAdapter = MessageAdapter(MessageLab.receiveMessage,this)
                }
            }
        }
        message_rv.adapter = messageAdapter

    }


    fun refresh(){
        messageAdapter.notifyDataSetChanged()
    }
}
