package com.example.nuonuo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.bean.RegisterResponse
import com.example.nuonuo.presenter.RegisterPresenterImpl
import com.example.nuonuo.view.RegisterView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun init(){
        login_btn.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.login_btn ->{
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

}
