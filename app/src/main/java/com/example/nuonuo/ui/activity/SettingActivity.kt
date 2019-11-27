package com.example.nuonuo.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.UserInfo
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.marco.Constant.CHOOSE_PHOTO
import com.example.nuonuo.marco.Constant.CROP_REQUEST_CODE
import com.example.nuonuo.marco.Constant.TAKE_PHOTO
import com.example.nuonuo.presenter.ModifyPresenterImpl
import com.example.nuonuo.ui.fragment.HomeFragment
import com.example.nuonuo.utils.BitmapUtil
import com.example.nuonuo.utils.HeadImgUtil
import com.example.nuonuo.utils.ImageUtil
import com.example.nuonuo.utils.PopUpUtil
import com.example.nuonuo.view.ModifyView
import kotlinx.android.synthetic.main.activity_mine_setting.*
import kotlinx.android.synthetic.main.activity_mine_setting.modifyinfo_btn
import kotlinx.android.synthetic.main.activity_mine_setting.progressBar
import kotlinx.android.synthetic.main.activity_mine_setting.setting_title
import kotlinx.android.synthetic.main.activity_new_trend.*
import kotlinx.android.synthetic.main.layout_bottom_dialog.view.*
import java.io.File

class SettingActivity : AppCompatActivity(), View.OnClickListener,ModifyView{
    private lateinit var cameraUri: Uri
    private lateinit var cropUri: Uri

    private val modifyPresenterImpl: ModifyPresenterImpl by lazy {
        ModifyPresenterImpl(this)
    }

    private var uid: Int by Preference(Constant.UID_KEY,0)

    private var accessToken: String by Preference(Constant.ACCESS_TOKEN_KEY,"")

    private var phone: String by Preference(Constant.PHONE_KEY,"")

    private var name: String by Preference(Constant. USER_NAME_KEY,"")

    private var sexual: String by Preference(Constant.SEXUAL_KEY,"")

    private var qq: String? by Preference(Constant.QQ_KEY,"")

    private var weixin: String? by Preference(Constant.WEIXIN_KYE,"")

    private var headPicId: Int? by Preference(Constant.HEAD_PIC_ID_KEY,0)

    private var headPicUrl: String? by Preference(Constant.HEAD_PIC_URL_KEY,"")

    private lateinit var pop: PopupWindow


    private var selectHeadImg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_setting)
        init()
    }

    private fun init(){
        setting_title.setNavigationIcon(R.drawable.ic_navigate_before_white_24dp)
        setting_title.setNavigationOnClickListener {
            finish()
        }
        refreshData()
        setting_sex_tv1.setOnClickListener(this)
        modifyinfo_btn.setOnClickListener(this)
        setting_headimg_r1.setOnClickListener(this)
    }

    private fun refreshData(){
        setting_name_tv1.setText(name)
        setting_sex_tv1.text = sexual
        setting_qqnumber_tv1.setText(qq)
        setting_wechatnumber_tv1.setText(weixin)
        setting_phonenumber_tv1.text = phone
    }

    private fun showGenderPop() {
        val bottomView =
            View.inflate(this, R.layout.layout_bottom_mapdialog, null)
        val male = bottomView.findViewById<TextView>(R.id.baidumap_layout)
        val female = bottomView.findViewById<TextView>(R.id.gaodemap_layout)
        val other = bottomView.findViewById<TextView>(R.id.othermap_layout)
        male.text = "男"
        female.text = "女"
        other.text = "保密"
        val close = bottomView.findViewById<TextView>(R.id.mapdialog_closebtn)
        pop = PopupWindow(bottomView, -1, -2)
        pop.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pop.isOutsideTouchable = true
        pop.isFocusable = true

        val lp = this.window.attributes
        lp.alpha = 0.5f
        this.window.attributes = lp
        pop.setOnDismissListener {
            val lp = window.attributes
            lp.alpha = 1f
            window.attributes = lp
        }
        pop.animationStyle = R.style.main_menu_photo_anim
        pop.showAtLocation(this.window.decorView, Gravity.BOTTOM, 0, 0)

        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.baidumap_layout//男
                -> {
                    setting_sex_tv1.text = "男"
                }
                R.id.gaodemap_layout -> {
                    setting_sex_tv1.text = "女"
                }
                R.id.othermap_layout -> {
                    //保密
                    setting_sex_tv1.text = "保密"
                }
                R.id.mapdialog_closebtn -> {
                }
            }
            PopUpUtil.closePopupWindow(pop)
        }

        male.setOnClickListener(clickListener)
        female.setOnClickListener(clickListener)
        other.setOnClickListener(clickListener)
        close.setOnClickListener(clickListener)
    }


    private fun showImagePop() {
        val bottomView = View.inflate(this, R.layout.layout_bottom_dialog, null)
        val lp = this.window.attributes
        lp.alpha = 0.5f
        window.attributes = lp

        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.tv_album ->
                    //相册
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) !== PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                        )
                    } else {
                        ImageUtil.openAlbum(this)
                    }
                R.id.tv_camera ->
                    //拍照
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) !== PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            2
                        )
                    } else
                        cameraUri = ImageUtil.getPhotoByCamera(this)!!
                R.id.tv_cancel -> {
                }
            }//取消
            pop?.run {
                PopUpUtil.closePopupWindow(this)
            }
            //closePopupWindow();
        }
        pop = PopupWindow(bottomView, -1, -2)
        pop?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isOutsideTouchable = true
            isFocusable = true

            setOnDismissListener {
                val lp = window.attributes
                lp.alpha = 1f
                window.attributes = lp
            }
            animationStyle = R.style.main_menu_photo_anim
            showAtLocation(window.decorView, Gravity.BOTTOM, 0, 0)


        bottomView.tv_album.setOnClickListener(clickListener)
        bottomView.tv_camera.setOnClickListener(clickListener)
        bottomView.tv_cancel.setOnClickListener(clickListener)
            }
    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.setting_sex_tv1 -> {
                showGenderPop()
            }
            R.id.modifyinfo_btn ->{
                modify()
            }
            R.id.setting_headimg_r1 ->{
                showImagePop()
            }
        }
    }


    override fun modify() {
        PopUpUtil.showProgressBar(window,progressBar)
        val userInfo = UserInfo(
            setting_name_tv1.text.toString(),
            setting_sex_tv1.text.toString(),
            setting_qqnumber_tv1.text.toString(),
            setting_wechatnumber_tv1.text.toString(),
            null
        )
        modifyPresenterImpl.modify(userInfo,accessToken)
    }

    override fun modifySuccess(result: LoginResponse) {
        name = setting_name_tv1.text.toString()
        sexual = setting_sex_tv1.text.toString()
        qq = setting_qqnumber_tv1.text.toString()
        weixin = setting_wechatnumber_tv1.text.toString()
        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show()
        PopUpUtil.cancelProgressBar(window,progressBar)

    }

    override fun modifyFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
        PopUpUtil.cancelProgressBar(window,progressBar)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this,"拍照成功",Toast.LENGTH_SHORT).show()
                try {
                    cropUri = ImageUtil.cropPhotoForCircle(this,cameraUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            CHOOSE_PHOTO -> {// 图片选择结果回调
                if (resultCode == Activity.RESULT_OK) {
                    var path: String? = ""
                    if (data?.data != null) {
                        path = if (Build.VERSION.SDK_INT >= 19) {
                            ImageUtil.handleImageOnKitKat(this, data.data)
                        } else {
                            ImageUtil.handleImageBeforeKitKat(this, data.data)
                        }

                    }

                    if ("" != path) {
                        val file = File(path)
                        cropUri = if (Build.VERSION.SDK_INT >= 24) {
                            FileProvider.getUriForFile(
                                this,
                                "com.example.nuonuo.provider", file
                            )
                        } else {
                            Uri.fromFile(file)
                        }
                        cropUri = ImageUtil.cropPhotoForCircle(this,cropUri)
                    }
                }
            }
            CROP_REQUEST_CODE ->{
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this,"切图成功",Toast.LENGTH_SHORT).show()
                    selectHeadImg = BitmapUtil.compressImage(
                        "$externalCacheDir/small.jpg",
                        externalCacheDir.path
                    )
                    val options = HeadImgUtil.getHeadImgOptions("")
                    Glide.with(this).load(selectHeadImg).apply(options).into(mine_setting_head)
                }else{
                    Toast.makeText(this,"切图失败",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImageUtil.openAlbum(this)
            } else {
                Toast.makeText(this, "您已拒绝读取SD卡权限，请前往设置授权该应用", Toast.LENGTH_SHORT).show()
            }
            2 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraUri = ImageUtil.getPhotoByCamera(this)
            } else {
                Toast.makeText(this, "您已拒绝读拍照，请前往设置授权该应用", Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }

}
