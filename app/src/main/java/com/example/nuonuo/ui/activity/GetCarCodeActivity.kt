package com.example.nuonuo.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import com.example.nuonuo.utils.BitmapUtil
import kotlinx.android.synthetic.main.activity_get_car_code.*

class GetCarCodeActivity : BaseActivity(), View.OnClickListener {

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
}
