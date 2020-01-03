package com.example.nuonuo.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mykotlin.base.BaseWithImmersionActivity
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.marco.Constant
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : BaseWithImmersionActivity(), View.OnClickListener{
    private var accessToken: String by Preference(Constant.ACCESS_TOKEN_KEY,"")
    companion object {
        const val START_REGISTER = 0
        const val START_LOGIN = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Preference.setContext(applicationContext)
        when(accessToken){
            "" -> {
                setContentView(R.layout.activity_first)
                init()
            }
            else ->{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }

    fun init(){
        goToRegisterText.setOnClickListener(this)
        register_rl.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.goToRegisterText ->{
                startActivityForResult(Intent(this,LoginActivity::class.java), START_LOGIN)
            }
            R.id.register_rl ->{
                startActivityForResult(Intent(this,RegisterWithImmersActivity::class.java),START_REGISTER)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            START_REGISTER -> {
                when (resultCode) {
                    RESULT_OK -> {
                        finish()
                    }
                }
            }
            START_LOGIN ->{
                when(resultCode){
                    Activity.RESULT_OK ->{
                        finish()
                    }
                }
            }
        }
    }
}
