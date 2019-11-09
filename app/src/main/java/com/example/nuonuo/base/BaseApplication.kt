package com.example.mykotlin.base

import android.app.Application
import com.example.mykotlin.DelegatesExt

class BaseApplication: Application() {
    companion object {
        private var instance: Application by DelegatesExt.notNullSingleValue()
        fun instance() = instance!!
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        Preference.setContext(applicationContext)
    }
}