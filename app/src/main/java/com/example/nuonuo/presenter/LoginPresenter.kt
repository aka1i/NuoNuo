package com.example.nuonuo.presenter

import com.example.nuonuo.bean.LoginResponse

/**
@author yjn
@create 2019/11/12 - 16:06
 */
interface LoginPresenter {
    interface OnLoginListener{
        fun login(username: String,password: String)

        fun loginSuccess(loginResponse: LoginResponse)


        fun loginFailed(errorMessage: String?)
    }


}