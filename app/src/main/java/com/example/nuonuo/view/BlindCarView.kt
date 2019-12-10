package com.example.nuonuo.view

import com.example.nuonuo.base.ErrorCallBackView
import com.example.nuonuo.bean.FileBean
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.SelfCarCodeResponse

/**
@author yjn
@create 2019/11/28 - 23:07
 */
interface BlindCarView: ErrorCallBackView {

    fun blindCar(code: String,fileBean: FileBean,accessToken:String)

    fun blindSuccess(loginResponse: LoginResponse)

    fun blindFailed(errorMessage:String?)

    fun getSelfCarCode(accessToken:String)

    fun getSelfCarCodeSuccess(selfCarCodeResponse: SelfCarCodeResponse)

    fun getSelfCarCodeFailed(errorMessage: String?)

}