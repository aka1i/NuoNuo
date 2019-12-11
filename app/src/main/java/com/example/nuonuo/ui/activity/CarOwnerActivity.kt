package com.example.nuonuo.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mykotlin.base.BaseWithImmersionActivity
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.CarOwnerPresenterImpl
import com.example.nuonuo.utils.HeadImgUtil
import com.example.nuonuo.utils.PopUpUtil
import com.example.nuonuo.view.CarOwnerView
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_car_owner.*
import kotlin.properties.Delegates

class CarOwnerActivity : BaseWithImmersionActivity(), View.OnClickListener,CarOwnerView {

    companion object{
        fun newIntent(context: Context, uid: Int, name: String,headUrl: String, phone:String): Intent{
            val intent = Intent(context,CarOwnerActivity::class.java)
            intent.putExtra("uid",uid)
            intent.putExtra("headUrl", headUrl)
            intent.putExtra("name",name)
            intent.putExtra("phone",phone)
            return intent
        }
    }

    private var uid by Delegates.notNull<Int>()

    private lateinit var phone: String

    private var accessToken: String by Preference(Constant.ACCESS_TOKEN_KEY,"")

    private val carOwnerPresenterImpl: CarOwnerPresenterImpl by lazy {
        CarOwnerPresenterImpl(this)
    }

    private var popWindow: PopupWindow? = null

    private var applyButton: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_owner)
        init()
    }

    fun init(){
        val options = HeadImgUtil.getHeadImgOptions("")
        Glide.with(this)
            .load(intent.getStringExtra("headUrl"))
            .apply(options)
            .into(car_owner_head_img)
        Glide.with(this)
            .load(R.drawable.test_bg)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(10)))
            .into(car_owner_bg)
        uid = intent.getIntExtra("uid",0)
        phone = intent.getStringExtra("phone")
        message_rl.setOnClickListener(this)
        car_owner_name.text = intent.getStringExtra("name")
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.message_rl ->{
                showMessagePop()
            }
        }
    }

    private fun showMessagePop() {
        val popView = View.inflate(this, R.layout.layout_daohang_title_pop, null)

        popWindow = PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popWindow?.run {

            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isOutsideTouchable = true
            isFocusable = true
            val lp = window.attributes
            lp.alpha = 0.5f
            window.attributes = lp
            setOnDismissListener {
                val lp = window.attributes
                PopUpUtil.cancelProgressBar(window,popView.findViewById(R.id.progressBar))
                lp.alpha = 1f
                window.attributes = lp
            }
            animationStyle = R.style.main_menu_photo_anim
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            showAtLocation(window.decorView, Gravity.CENTER, 0, 0)
            applyButton = popView.findViewById<ImageView>(R.id.applyButton)
            applyButton?.setOnClickListener {
                it.isClickable = false
                carOwnerPresenterImpl.senMessage(popView.findViewById<EditText>(R.id.message_et).text.toString(),accessToken,uid,phone)
                PopUpUtil.showProgressBar(window,popView.findViewById(R.id.progressBar))

            }
        }
    }

    override fun senMessage(content: String, accessToken: String, uid: Int,phone: String) {
        carOwnerPresenterImpl.senMessage(content,accessToken,uid,phone)
    }

    override fun senMessageeFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT).show()
        applyButton?.isClickable = true
    }

    override fun senMessageSuccess() {
        Toast.makeText(this,"留言成功", Toast.LENGTH_SHORT).show()
        popWindow?.dismiss()
    }
}
