package com.example.mykotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nuonuo.base.BaseActivity
import com.gyf.immersionbar.ImmersionBar


open class BaseWithImmersionActivity: BaseActivity()  {
    protected lateinit var immersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initImmersionBar()
    }

    open protected fun initImmersionBar() {
        //在BaseActivity里初始化
        immersionBar = ImmersionBar.with(this)
        immersionBar
            .init()
    }

}