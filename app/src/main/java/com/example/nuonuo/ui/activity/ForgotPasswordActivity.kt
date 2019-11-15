package com.example.nuonuo.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.presenter.ForgotPasswordPresenterImpl
import com.example.nuonuo.view.ForgotPasswordView
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity(),ForgotPasswordView,View.OnClickListener {

    private val forgotPasswordPresenterImpl: ForgotPasswordPresenterImpl by lazy {
        ForgotPasswordPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        init()
    }

    private fun init(){
        getCodeButton.setOnClickListener(this)
        applyButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val email = forgotPassword_email_et.text.toString().trim()
        val password = forgotPassword_password_et.text.toString().trim()
        val checkPassword = forgotPassword_check_et.text.toString().trim()
        val code = forgotPassword_code_et.text.toString().trim()
        when(v?.id){
            R.id.getCodeButton -> {
                if (email.isEmpty()){
                    Toast.makeText(this,"邮箱不能为空", Toast.LENGTH_SHORT).show()
                } else if(!email.matches(regex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"))) {
                    Toast.makeText(this, "邮箱格式有误", Toast.LENGTH_SHORT).show()
                }else
                    forgotPasswordPresenterImpl.getEmailCode(email)
            }
            R.id.applyButton ->{
                if (password.isEmpty()){
                    Toast.makeText(this,"密码不能为空", Toast.LENGTH_SHORT).show()
                }
                else if (password != checkPassword){
                    Toast.makeText(this,"二次密码不一样", Toast.LENGTH_SHORT).show()

                }else if (email.isEmpty()){
                    Toast.makeText(this,"邮箱不能为空", Toast.LENGTH_SHORT).show()
                } else if(!email.matches(regex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"))){
                    Toast.makeText(this,"邮箱格式有误", Toast.LENGTH_SHORT).show()
                }else if (code.isEmpty()){
                    Toast.makeText(this,"验证码不能为空", Toast.LENGTH_SHORT).show()
                }
                else{
                    forgotPasswordPresenterImpl.forgotPassword(email,password,code)
                }
            }
        }
    }

    override fun forgotPasswordSuccess() {
        Toast.makeText(this,"重置密码成功", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun forgotPasswordFailed(error: String?) {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show()
    }

    override fun getEmailCodeSuccess() {
        Toast.makeText(this,"获取验证码成功", Toast.LENGTH_SHORT).show()

    }

    override fun getEmailCodeFail(error: String?) {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show()
    }
}
