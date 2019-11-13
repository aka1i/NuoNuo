package com.example.nuonuo.ui.activity

import android.os.Bundle
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.view.ForgotPasswordView

class ForgotPasswordActivity : BaseActivity(),ForgotPasswordView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    override fun forgotPasswordSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun forgotPasswordFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmailCodeSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmailCodeFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
