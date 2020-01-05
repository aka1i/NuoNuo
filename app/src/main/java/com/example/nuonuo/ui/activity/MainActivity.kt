package com.example.nuonuo.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.jpush.im.android.api.JMessageClient
import com.example.mykotlin.base.BaseWithImmersionActivity
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.adapter.MainActivityPagerAdapter
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.im.GlobalEventListener
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.LoginPresenterImpl
import com.example.nuonuo.presenter.MainViewPresenterImpl
import com.example.nuonuo.ui.fragment.HomeFragment
import com.example.nuonuo.ui.fragment.MessageFragment
import com.example.nuonuo.ui.fragment.MineFragment
import com.example.nuonuo.ui.fragment.TrendsFragment
import com.example.nuonuo.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseWithImmersionActivity() ,MainView{

    private val homeFragment = HomeFragment()

    private val trendsFragment = TrendsFragment()

    private val messageFragment = MessageFragment()

    private val mineFragment = MineFragment()

    private val mainViewPresenterImpl = MainViewPresenterImpl(this)

    private val accessToken by Preference(Constant.ACCESS_TOKEN_KEY,"")

    private var score:Int by Preference(Constant.SCORE_KEY,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    override fun onDestroy() {
        super.onDestroy()
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
        getMyInfo(accessToken)
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


    override fun getMyInfo(accessToken: String) {
        mainViewPresenterImpl.getMyInfo(accessToken)
    }

    override fun getMyInfoSuccess(result: LoginResponse) {
        score = result.data.score
        trendsFragment.reFreshMyData()
        mineFragment.refreshSelfData()
    }

    override fun getMyInfoFailed(errorMessage: String?) {
    }
}
