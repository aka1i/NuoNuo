package com.example.nuonuo.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.adapter.MainActivityPagerAdapter
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.ui.fragment.HomeFragment
import com.example.nuonuo.ui.fragment.MessageFragment
import com.example.nuonuo.ui.fragment.MineFragment
import com.example.nuonuo.ui.fragment.TrendsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val homeFragment = HomeFragment()

    private val trendsFragment = TrendsFragment()

    private val messageFragment = MessageFragment()

    private val mineFragment = MineFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        val adapter = MainActivityPagerAdapter(supportFragmentManager)
        adapter.addFragment(homeFragment)
        adapter.addFragment(trendsFragment)
        adapter.addFragment(messageFragment)
        adapter.addFragment(mineFragment)
        viewPager.adapter = adapter
        viewPager.isScrollble = false
        viewPager.offscreenPageLimit = 4
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode,data)
        when(requestCode){
            Constant.START_SETTING ->{
                when(resultCode){
                    Activity.RESULT_OK -> {
                        trendsFragment.refreshSelfData()
                        mineFragment.refreshSelfData()
                    }
                }
            }
        }
    }
}
