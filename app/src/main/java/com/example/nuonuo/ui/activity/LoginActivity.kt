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
import com.example.nuonuo.ui.custom.SmartLoadingView
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

    private var result:LoginResponse? = null

    private var errorMessage:String? = null

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
        login_btn.myListener = object :SmartLoadingView.MyListener{
            override fun onSuccess() {
                result?.run{
                    uid = this.data.uid
                    accessToken = this.data.accessToken
                    phone = this.data.phone
                    name = this.data.name
                    sexual = this.data.sexual
                    qq = this.data.qq
                    weixin = this.data.weixin
                    headPicId = this.data.headPicId
                    headPicUrl = this.data.headPicUrl

                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }

            override fun onFailed() {
                Toast.makeText(this@LoginActivity,errorMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun loginFailed(errorMessage: String?) {
        this.errorMessage = errorMessage
        login_btn.failed()
    }

    override fun loginSuccess(result: LoginResponse) {
        this.result = result
        login_btn.success()
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
                        login_btn.start()
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
