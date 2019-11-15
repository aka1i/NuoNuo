package com.example.nuonuo.ui.activity

import android.os.Bundle
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.adapter.MainActivityPagerAdapter
import com.example.nuonuo.ui.fragment.HomeFragment
import com.example.nuonuo.ui.fragment.MessageFragment
import com.example.nuonuo.ui.fragment.MineFragment
import com.example.nuonuo.ui.fragment.TrendsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        val adapter = MainActivityPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(TrendsFragment())
        adapter.addFragment(MessageFragment())
        adapter.addFragment(MineFragment())
        viewPager.adapter = adapter
        viewPager.isScrollble = false
        bottom_navigation.setOnNavigationItemSelectedListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home ->{
                    viewPager.setCurrentItem(0,false)
                }
                R.id.navigation_find -> {
                    viewPager.setCurrentItem(1,false)
                }
                R.id.navigation_order -> {
                    viewPager.setCurrentItem(2,false)
                }
                R.id.navigation_mine -> {
                    viewPager.setCurrentItem(3,false)
                }
            }
            true
        }
    }
}
