package com.example.nuonuo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mykotlin.base.BaseActivity
import com.example.nuonuo.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_car_owner.*

class CarOwnerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_owner)
        init()
    }

    fun init(){
        Glide.with(this)
            .load(R.drawable.test_bg)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(10)))
            .into(car_owner_bg)
    }
}
