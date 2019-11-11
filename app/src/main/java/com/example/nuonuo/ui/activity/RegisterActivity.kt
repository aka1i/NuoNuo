package com.example.nuonuo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.bean.RegisterResponse
import com.example.nuonuo.presenter.RegisterPresenterImpl
import com.example.nuonuo.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*




class RegisterActivity : BaseActivity(), RegisterView,View.OnClickListener{

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
                    Log.d("RetrofitHelper","onclick")
                    registerPresenterImpl.register(userName,password,email)
                }

            }
        }
    }

    override fun registerSuccess(result: RegisterResponse) {
        Toast.makeText(this,result.message, Toast.LENGTH_SHORT).show()
    }

    override fun registerFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT).show()
    }

}
