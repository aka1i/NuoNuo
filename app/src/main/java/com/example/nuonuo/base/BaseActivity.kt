package com.example.nuonuo.base

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
@author yjn
@create 2019/12/10 - 15:56
 */
open class BaseActivity : AppCompatActivity(), ErrorCallBackView {

    private val TAG = "BaseActivity_"

    override fun errorCallBack_1001(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()

        Log.d(TAG + "1001",errorMessage)
    }
}