package com.example.nuonuo.view

/**
@author yjn
@create 2019/11/12 - 23:27
 */
interface ForgotPasswordView {
    fun forgotPasswordSuccess()

    fun forgotPasswordFailed(error: String?)

    fun getEmailCodeSuccess()

    fun getEmailCodeFail(error: String?)
}