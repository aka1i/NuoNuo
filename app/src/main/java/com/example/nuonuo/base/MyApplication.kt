package com.example.nuonuo.base

import android.app.Application
import android.util.Log
import cn.jpush.im.android.api.JMessageClient
import com.example.nuonuo.im.GlobalEventListener

/**
@author yjn
@create 2019/12/11 - 17:56
 */
class MyApplication: Application() {
    override fun onCreate() {
        Log.d("IMDebugApplication", "init")

        super.onCreate()

        JMessageClient.setDebugMode(true)
        JMessageClient.init(applicationContext, true)
        //注册全局事件监听类
        JMessageClient.registerEventReceiver(GlobalEventListener(applicationContext))
    }



}