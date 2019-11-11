package com.example.nuonuo.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.example.nuonuo.R
import com.example.nuonuo.adapter.MainActivityPagerAdapter
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.viewPager
import kotlinx.android.synthetic.main.tab_item.view.*

class MessageFragment : Fragment() {
    private val titleText = arrayOf("发送", "收到")
    private val icons =
        intArrayOf(R.drawable.message_receive, R.drawable.message_send)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val adapter = fragmentManager?.let { MainActivityPagerAdapter(it) }
        adapter?.addFragment(HomeFragment())
        adapter?.addFragment(TreandsFragment())

        viewPager.adapter = adapter

        initTab()
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

}
