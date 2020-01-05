package com.example.nuonuo.view

import com.example.nuonuo.bean.LoginResponse

/**
@author yjn
@create 2020/1/5 - 21:56
 */
interface MainView {

    fun getMyInfo(accessToken:String)

    fun getMyInfoSuccess(result:LoginResponse)

    fun getMyInfoFailed(errorMessage:String?)

}