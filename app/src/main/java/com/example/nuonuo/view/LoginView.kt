package com.example.nuonuo.view

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.RegisterResponse

/**
@author yjn
@create 2019/11/12 - 13:29
 */
interface LoginView {
    fun loginFailed(errorMessage: String?)

    fun loginSuccess(result: LoginResponse)
}