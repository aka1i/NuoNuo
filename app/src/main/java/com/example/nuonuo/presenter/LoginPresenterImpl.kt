package com.example.nuonuo.presenter

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.model.LoginModelImpl
import com.example.nuonuo.view.LoginView

/**
@author yjn
@create 2019/11/12 - 16:07
 */
class LoginPresenterImpl(private val loginView: LoginView): LoginPresenter.OnLoginListener {
    private val loginModelImpl: LoginModelImpl = LoginModelImpl()
    override fun login(username: String, password: String) {
        loginModelImpl.login(this,username,password)
    }

    override fun loginSuccess(loginResponse: LoginResponse) {
        loginView.loginSuccess(loginResponse)
    }

    override fun loginFailed(errorMessage: String?) {
        loginView.loginFailed(errorMessage)
    }
}