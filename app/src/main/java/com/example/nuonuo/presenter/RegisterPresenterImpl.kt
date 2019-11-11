package com.example.nuonuo.presenter

import com.example.nuonuo.bean.RegisterResponse
import com.example.nuonuo.model.RegisterModel
import com.example.nuonuo.model.RegisterModelImpl
import com.example.nuonuo.view.RegisterView

/**
@author yjn
@create 2019/11/10 - 19:16
 */
class RegisterPresenterImpl(private val registerView : RegisterView): RegisterPresenter.OnRegisterListener {
    private val registerModel: RegisterModel = RegisterModelImpl()

    override fun register(username: String, password: String, email: String) {
        registerModel.register(this,username,password,email)
    }

    override fun registerSuccess(result: RegisterResponse) {
        registerView.registerSuccess(result)
    }

    override fun registerFailed(errorMessage: String?) {
        registerView.registerFailed(errorMessage)
    }

}