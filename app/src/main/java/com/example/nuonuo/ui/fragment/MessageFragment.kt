package com.example.nuonuo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mykotlin.base.Preference


import com.example.nuonuo.R
import com.example.nuonuo.adapter.MainActivityPagerAdapter
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.MessagePresenterImpl
import com.example.nuonuo.view.MessageListView
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.viewPager
import kotlinx.android.synthetic.main.tab_item.view.*

class MessageFragment : Fragment(), MessageListView {

    private var accessToken: String by Preference(Constant.ACCESS_TOKEN_KEY,"")

    private var uid: Int by Preference(Constant.UID_KEY,0)

    private val titleText = arrayOf("发送", "收到")
    private val icons =
        intArrayOf(R.drawable.message_receive, R.drawable.message_send)

    private val messagePresenterImpl: MessagePresenterImpl by lazy {
        MessagePresenterImpl(this)
    }

    private val sendFragment: MessageListFragment by lazy {
        MessageListFragment(MessageListFragment.TYPE_SEND)
    }

    private val receiveFragment: MessageListFragment by lazy {
        MessageListFragment(MessageListFragment.TYPE_RECEIVE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener {
            when(viewPager.currentItem){
                0 -> {
                    messagePresenterImpl.getSend(accessToken,uid)
                }
                1 ->{
                    messagePresenterImpl.getRecive(accessToken,uid)
                }
            }
        }
        val adapter = childFragmentManager?.let { MainActivityPagerAdapter(it) }
        adapter?.addFragment(sendFragment)
        adapter?.addFragment(receiveFragment)
        viewPager.isScrollble = false
        viewPager.adapter = adapter
        initTab()
        messagePresenterImpl.getSend(accessToken,uid)
        messagePresenterImpl.getRecive(accessToken,uid)
    }

    private fun initTab() {
        tabLayout.setupWithViewPager(viewPager)
        for (i in titleText.indices) {
            val v = LayoutInflater.from(activity).inflate(R.layout.tab_item, null)
            v.tab_text.text = titleText[i]
            v.tab_icon.setImageDrawable(resources.getDrawable(icons[i]))
            val tab = tabLayout.getTabAt(i)
            tab?.customView = v
        }
    }

    override fun getSendFailed(errorMessage: String?) {
        stopRefresh()
        Toast.makeText(activity,errorMessage, Toast.LENGTH_SHORT).show()

    }

    override fun getSentSuccess() {
        stopRefresh()
        sendFragment.refresh()
    }

    override fun getReceiveFailed(errorMessage: String?) {
        stopRefresh()
        Toast.makeText(activity,errorMessage, Toast.LENGTH_SHORT).show()

    }

    override fun getReceiveSuccess() {
        stopRefresh()
        receiveFragment.refresh()
    }

    override fun stopRefresh() {
        if (swipeRefreshLayout.isRefreshing)
            swipeRefreshLayout.isRefreshing = false
    }
}
