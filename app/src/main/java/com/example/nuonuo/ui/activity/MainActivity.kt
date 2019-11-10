package com.example.nuonuo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.adapter.MainActivityPagerAdapter
import com.example.nuonuo.ui.fragment.HomeFragment
import com.example.nuonuo.ui.fragment.MessageFragment
import com.example.nuonuo.ui.fragment.MineFragment
import com.example.nuonuo.ui.fragment.TreandsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        adapter.addFragment(TreandsFragment())
        adapter.addFragment(MessageFragment())
        adapter.addFragment(MineFragment())
        viewPager.adapter = adapter

        bottom_navigation.setOnNavigationItemSelectedListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_find -> viewPager.currentItem = 1
                R.id.navigation_order -> viewPager.currentItem = 2
                R.id.navigation_mine -> viewPager.currentItem = 3
            }
            true
        }
    }
}
