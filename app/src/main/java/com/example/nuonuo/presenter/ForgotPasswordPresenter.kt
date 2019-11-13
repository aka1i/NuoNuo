package com.example.nuonuo.presenter

import com.example.nuonuo.bean.LoginResponse

/**
@author yjn
@create 2019/11/12 - 23:30
 */
interface ForgotPasswordPresenter {
    interface OnForgotPasswordListener{
        fun getEmailCode(email: String)

        fun getEmailSuccess(loginResponse: LoginResponse)


        fun getEmailFailed(errorMessage: String?)

        fun forgotPassword(email: String,password:String,code: String)

        fun forgotPasswordSuccess(loginResponse: LoginResponse)


        fun forgotPasswordFailed(errorMessage: String?)
    }
}