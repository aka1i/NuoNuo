package com.example.nuonuo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mykotlin.base.BaseActivity
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.bean.BaiduOCRResponse
import com.example.nuonuo.bean.BaiduTokenResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.GetCarCodePresenterImpl
import com.example.nuonuo.utils.BitmapUtil
import com.example.nuonuo.view.GetCarCodeView
import kotlinx.android.synthetic.main.activity_get_car_code.*
import java.util.*

class GetCarCodeActivity : AppCompatActivity(), View.OnClickListener,GetCarCodeView {

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

        if (baidu_api_access_token == "" || (Date().time >= baidu_api_expires_in && baidu_api_expires_in != 0.toLong()))
            baiduAPIPresenterImpl.getToken()
        else
            baiduAPIPresenterImpl.getORC(BitmapUtil.compressImage(
                "$externalCacheDir/small.jpg",
                externalCacheDir.path
            ),baidu_api_access_token)
        Log.d("GETORC",BitmapUtil.compressImage(
            "$externalCacheDir/small.jpg",
            externalCacheDir.path
        ))
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.car_photo_back ->{
                finish()
            }
            R.id.applyButton -> {
                val intent = Intent(this,SelectOwnerActivity::class.java)
                intent.putExtra("code",car_code_text.text.toString())
                startActivity(intent)
            }
        }
    }



    override fun getTokenSuccess(baiduTokenResponse: BaiduTokenResponse) {
        Toast.makeText(this,baiduTokenResponse.toString(),Toast.LENGTH_SHORT).show()
        baidu_api_expires_in = Date().time + (baiduTokenResponse.expires_in * 1000)
        baidu_api_access_token = baiduTokenResponse.access_token
        Toast.makeText(this,baiduTokenResponse.toString(),Toast.LENGTH_SHORT).show()
        baiduAPIPresenterImpl.getORC(BitmapUtil.compressImage(
            "$externalCacheDir/small.jpg",
            externalCacheDir.path
        ),baidu_api_access_token)
    }

    override fun getTokenFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
    }


    override fun getCarCodeSuccess(baiduOCRResponse: BaiduOCRResponse) {
        car_code_text.text = baiduOCRResponse.toString()
    }

    override fun getCarCodeFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
    }
}
