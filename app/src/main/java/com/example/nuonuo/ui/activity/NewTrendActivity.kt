package com.example.nuonuo.ui.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.bean.NewTrend
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.NewTrendPresenterImpl
import com.example.nuonuo.utils.PopUpUtil
import com.example.nuonuo.view.NewTrendView
import kotlinx.android.synthetic.main.activity_new_trend.*

class NewTrendActivity : AppCompatActivity(),NewTrendView, View.OnClickListener {

    private var accessToken: String by Preference(Constant.ACCESS_TOKEN_KEY,"")

    private val newTrendPresenterImpl:NewTrendPresenterImpl by lazy {
        NewTrendPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trend)
        init()
    }

    private fun init(){
        setting_title.setNavigationIcon(R.drawable.ic_navigate_before_white_24dp)
        setting_title.setNavigationOnClickListener {
            finish()
        }

        modifyinfo_btn.setOnClickListener(this)

    }


    override fun newTrend() {
        PopUpUtil.showProgressBar(window,progressBar)
        newTrendPresenterImpl.newTrend(NewTrend(
            null,
            content_et.text.toString().trim(),
            null
        ),
            accessToken)
    }

    override fun newTrendFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
        PopUpUtil.cancelProgressBar(window,progressBar)

    }

    override fun newTrendSuccess() {
        Toast.makeText(this,"发布成功",Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK)
        PopUpUtil.cancelProgressBar(window,progressBar)
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.modifyinfo_btn ->{
                newTrend()
            }
        }
    }
}
