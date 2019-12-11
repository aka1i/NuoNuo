package com.example.nuonuo.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mykotlin.base.BaseWithImmersionActivity
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.LoginPresenterImpl
import com.example.nuonuo.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseWithImmersionActivity(),LoginView, View.OnClickListener {

    private var uid: Int by Preference(Constant.UID_KEY,0)

    private var accessToken: String by Preference(Constant.ACCESS_TOKEN_KEY,"")

    private var phone: String by Preference(Constant.PHONE_KEY,"")

    private var name: String by Preference(Constant. USER_NAME_KEY,"")

    private var sexual: String by Preference(Constant.SEXUAL_KEY,"")

    private var qq: String? by Preference(Constant.QQ_KEY,"")

    private var weixin: String? by Preference(Constant.WEIXIN_KYE,"")


    private var headPicId: Int? by Preference(Constant.HEAD_PIC_ID_KEY,0)

    private var headPicUrl: String? by Preference(Constant.HEAD_PIC_URL_KEY,"")


    private val loginPresenterImpl: LoginPresenterImpl by lazy {
        LoginPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun init(){
        login_btn.setOnClickListener(this)
        forgotPasswordTextView.setOnClickListener(this)
    }


    override fun loginFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccess(result: LoginResponse) {
        uid = result.data.uid
        accessToken = result.data.accessToken
        phone = result.data.phone
        name = result.data.name
        sexual = result.data.sexual
        qq = result.data.qq
        weixin = result.data.weixin
        headPicId = result.data.headPicId
        headPicUrl = result.data.headPicUrl

        startActivity(Intent(this,MainActivity::class.java))
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.login_btn ->{
                val username = register_user_et.text.toString().trim()
                val password  = register_password_et.text.toString().trim()
                when{
                    username.isEmpty() ->{
                        Toast.makeText(this,"用户名不能为空", Toast.LENGTH_SHORT).show()
                    }
                    password.isEmpty() ->{
                        Toast.makeText(this,"密码不能为空", Toast.LENGTH_SHORT).show()
                    }
                    else ->{
                        loginPresenterImpl.login(username,password)
                    }
                }
            }
            R.id.forgotPasswordTextView -> {
                startActivity(Intent(this,ForgotPasswordActivity::class.java))
            }
        }
    }

}
