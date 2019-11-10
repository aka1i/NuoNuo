package com.example.nuonuo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : BaseActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        init()
    }

    fun init(){
        goToRegisterText.setOnClickListener(this)
        register_rl.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.goToRegisterText ->{
                startActivity(Intent(this,LoginActivity::class.java))
            }
            R.id.register_rl ->{
                startActivity(Intent(this,RegisterActivity::class.java))
            }
        }
    }
}
