package com.example.nuonuo.presenter

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.model.ForgotPasswordModelImpl
import com.example.nuonuo.view.ForgotPasswordView

/**
@author yjn
@create 2019/11/12 - 23:30
 */
class ForgotPasswordPresenterImpl(private val forgotPasswordView: ForgotPasswordView):ForgotPasswordPresenter.OnForgotPasswordListener {

    private val forgotPasswordModelImpl: ForgotPasswordModelImpl = ForgotPasswordModelImpl(this)

    override fun getEmailCode(email: String) {
        forgotPasswordModelImpl.getEamilCode(email)
    }

    override fun getEmailSuccess(loginResponse: LoginResponse) {
        forgotPasswordView.getEmailCodeSuccess()
    }

    override fun getEmailFailed(errorMessage: String?) {
        forgotPasswordView.getEmailCodeFail(errorMessage)
    }

    override fun forgotPassword(email: String, password: String, code: String) {
        forgotPasswordModelImpl.forgotPassword(email,password,code)
    }

    override fun forgotPasswordSuccess(loginResponse: LoginResponse) {
        forgotPasswordView.forgotPasswordSuccess()
    }

    override fun forgotPasswordFailed(errorMessage: String?) {
        forgotPasswordView.forgotPasswordFailed(errorMessage)
    }
}