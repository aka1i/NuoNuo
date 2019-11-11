package com.example.nuonuo.model

import com.example.nuonuo.presenter.RegisterPresenter

/**
@author yjn
@create 2019/11/10 - 19:27
 */
interface RegisterModel {

    fun register(onRegisterListener: RegisterPresenter.OnRegisterListener, username: String, password: String, email: String)

}