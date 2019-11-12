package com.example.nuonuo.model

import com.example.nuonuo.presenter.LoginPresenter

/**
@author yjn
@create 2019/11/12 - 16:09
 */
interface LoginModel {
    fun login(onLoginListener: LoginPresenter.OnLoginListener,username: String,password: String)
}