package com.example.nuonuo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.base.BaseActivity
import com.example.nuonuo.bean.BaiduOCRResponse
import com.example.nuonuo.bean.BaiduTokenResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.GetCarCodePresenterImpl
import com.example.nuonuo.utils.BaiduAPIParese
import com.example.nuonuo.utils.BitmapUtil
import com.example.nuonuo.utils.PopUpUtil
import com.example.nuonuo.view.GetCarCodeView
import kotlinx.android.synthetic.main.activity_get_car_code.*
import java.util.*

class GetCarCodeActivity : BaseActivity(), View.OnClickListener,GetCarCodeView {

    private val baiduAPIPresenterImpl: GetCarCodePresenterImpl by lazy {
        GetCarCodePresenterImpl(this)
    }


    private var baidu_api_access_token: String by Preference(Constant.BAIDU_API_ACCESS_TOKEN,"")

    private var baidu_api_expires_in: Long by Preference(Constant.BAIDU_API_EXPIRES_IN,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_car_code)
        init()
    }

    private fun init(){
        Glide.with(this).load(
            BitmapUtil.compressImage(
                "$externalCacheDir/small.jpg",
                externalCacheDir.path
            ))
            .centerCrop()
            .into(photoImg)

        car_photo_back.setOnClickListener(this)
        applyButton.setOnClickListener(this)


        PopUpUtil.showProgressBar(window,progressBar)
        if (baidu_api_access_token == "" || (Date().time >= baidu_api_expires_in && baidu_api_expires_in != 0.toLong()))
            baiduAPIPresenterImpl.getToken()
        else
            baiduAPIPresenterImpl.getORC(BitmapUtil.compressImage(
                "$externalCacheDir/small.jpg",
                externalCacheDir.path
            ),baidu_api_access_token)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.car_photo_back ->{
                finish()
            }
            R.id.applyButton -> {
                startActivity(SelectOwnerActivity.newIntent(this,car_code_text.text.toString()))
            }
        }
    }



    override fun getTokenSuccess(baiduTokenResponse: BaiduTokenResponse) {
        baidu_api_expires_in = Date().time + (baiduTokenResponse.expires_in.toLong() * 1000)
        baidu_api_access_token = baiduTokenResponse.access_token
        baiduAPIPresenterImpl.getORC(BitmapUtil.compressImage(
            "$externalCacheDir/small.jpg",
            externalCacheDir.path
        ),baidu_api_access_token)

    }

    override fun getTokenFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
        PopUpUtil.cancelProgressBar(window,progressBar)

    }


    override fun getCarCodeSuccess(baiduOCRResponse: BaiduOCRResponse) {
        val result = BaiduAPIParese.paraseORCResponse(baiduOCRResponse)
        if (result == null)
        {
            car_code_text.text = "未匹配到车牌"
            applyButton.visibility = View.INVISIBLE
        }
        else{
            car_code_text.text = result
            applyButton.visibility = View.VISIBLE
        }
        PopUpUtil.cancelProgressBar(window,progressBar)
    }

    override fun getCarCodeFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
        applyButton.visibility = View.INVISIBLE
        PopUpUtil.cancelProgressBar(window,progressBar)
    }
}
