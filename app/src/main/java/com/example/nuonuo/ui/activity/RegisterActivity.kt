package com.example.nuonuo.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mykotlin.base.BaseWithImmersionActivity
import com.example.nuonuo.R
import com.example.nuonuo.bean.RegisterResponse
import com.example.nuonuo.presenter.RegisterPresenterImpl
import com.example.nuonuo.ui.custom.SmartLoadingView
import com.example.nuonuo.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*




class RegisterWithImmersActivity : BaseWithImmersionActivity(), RegisterView,View.OnClickListener{

    private val registerPresenterImpl: RegisterPresenterImpl by lazy {
        RegisterPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    fun init(){
        registerButton.setOnClickListener(this)
        registerButton.myListener = object :SmartLoadingView.MyListener{
            override fun onSuccess() {
                setResult(RESULT_OK)
                finish()
            }

            override fun onFailed() {
                Toast.makeText(this@RegisterWithImmersActivity,"请重试", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.registerButton ->{
                val userName = register_user_et.text.toString().trim()
                val password = register_password_et.text.toString().trim()
                val checkPassword = register_check_et.text.toString().trim()
                val email = register_email_et.text.toString().trim()
                if (userName.isEmpty()){
                    Toast.makeText(this,"用户名不能为空", Toast.LENGTH_SHORT).show()
                }else if (password.isEmpty()){
                    Toast.makeText(this,"密码不能为空", Toast.LENGTH_SHORT).show()
                }
                else if (password != checkPassword){
                    Toast.makeText(this,"二次密码不一样", Toast.LENGTH_SHORT).show()

                }else if (email.isEmpty()){
                    Toast.makeText(this,"邮箱不能为空", Toast.LENGTH_SHORT).show()
                } else if(!email.matches(regex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"))){
                    Toast.makeText(this,"邮箱格式有误", Toast.LENGTH_SHORT).show()
                }
                else{
                    registerPresenterImpl.register(userName,password,email)
                    registerButton.start()
                }

            }
        }
    }

    override fun registerSuccess(result: RegisterResponse) {
        registerButton.success()
    }

    override fun registerFailed(errorMessage: String?) {
       registerButton.failed()
    }

}
