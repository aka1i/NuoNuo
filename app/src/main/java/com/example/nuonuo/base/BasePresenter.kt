package com.example.nuonuo.base

import org.json.JSONObject

/**
@author yjn
@create 2019/12/10 - 16:11
 */
open class BasePresenter(private val errorCallBackView: ErrorCallBackView) {

    fun dealCallBackDataByData(errorMessage: String?){
        val json = JSONObject(errorMessage)
        dispatchError(json.getInt("status"),json.getString("message"))
    }

    fun dispatchError(status: Int,errorMessage: String?){
        when(status){
            1001 -> {
                errorCallBack_1001(errorMessage)
            }
        }
    }

    fun errorCallBack_1001(errorMessage: String?){
        errorCallBackView.errorCallBack_1001(errorMessage)
    }

}