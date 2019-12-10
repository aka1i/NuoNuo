package com.example.nuonuo.presenter

import com.example.nuonuo.bean.FileBean
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.SelfCarCodeResponse

/**
@author yjn
@create 2019/12/10 - 11:34
 */
interface BlindCarPresenter {

    interface OnBlindCarListener{

        fun blindCar(code: String,fileBean: FileBean,accessToken:String)

        fun blindSuccess(loginResponse: LoginResponse)

        fun blindFailed(errorMessage:String?)

    }

    interface OnGetSelfCarCode{

        fun getSelfCarCode(accessToken:String)

        fun getSelfCarCodeSuccess(selfCarCodeResponse: SelfCarCodeResponse)

        fun getSelfCarCodeFailed(errorMessage: String?)

    }

}