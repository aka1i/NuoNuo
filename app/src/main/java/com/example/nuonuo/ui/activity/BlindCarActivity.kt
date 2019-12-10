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
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mykotlin.base.Preference
import com.example.nuonuo.R
import com.example.nuonuo.base.BaseActivity
import com.example.nuonuo.bean.*
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.model.BlindCarModelImpl
import com.example.nuonuo.presenter.BlindCarPresenterImpl
import com.example.nuonuo.presenter.GetCarCodePresenterImpl
import com.example.nuonuo.utils.*
import com.example.nuonuo.view.BlindCarView
import com.example.nuonuo.view.GetCarCodeView
import kotlinx.android.synthetic.main.activity_blind_car.*
import kotlinx.android.synthetic.main.activity_blind_car.progressBar
import kotlinx.android.synthetic.main.activity_mine_setting.*
import kotlinx.android.synthetic.main.layout_bottom_dialog.view.*
import java.io.File
import java.util.*

class BlindCarActivity : BaseActivity(),View.OnClickListener,GetCarCodeView,BlindCarView {

    private val baiduAPIPresenterImpl: GetCarCodePresenterImpl by lazy {
        GetCarCodePresenterImpl(this)
    }

    private val blindPresenterImpl: BlindCarPresenterImpl by lazy {
        BlindCarPresenterImpl(this)
    }

    private var baidu_api_access_token: String by Preference(Constant.BAIDU_API_ACCESS_TOKEN,"")

    private var baidu_api_expires_in: Long by Preference(Constant.BAIDU_API_EXPIRES_IN,0)

    private var accessToken: String by Preference(Constant.ACCESS_TOKEN_KEY,"")

    private var fileBean: FileBean = FileBean(null,null,null,null,null)
    private lateinit var cameraUri: Uri
    private lateinit var cropUri: Uri
    private var selectHeadImg: String? = null
    private lateinit var pop: PopupWindow
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blind_car)
        init()
    }

    private fun init(){
        take_photo.setOnClickListener(this)
        applyButton.setOnClickListener(this)
        getSelfCarCode(accessToken)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.take_photo ->{
                showImagePop()
            }
            R.id.applyButton ->{
                blindPresenterImpl.blindCar(car_code_text.text.toString(),fileBean,accessToken)
            }
        }
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
            applyButton.visibility = View.INVISIBLE
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constant.TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                try {
                    cropUri = ImageUtil.cropPhotoForRectangle(this,cameraUri, 3, 4)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Constant.CHOOSE_PHOTO -> {// 图片选择结果回调
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
                        cropUri = ImageUtil.cropPhotoForRectangle(this,cropUri, 3, 4)
                    }
                }
            }
            Constant.CROP_REQUEST_CODE ->{
                if (resultCode == Activity.RESULT_OK) {
                    selectHeadImg = BitmapUtil.compressImage(
                        "$externalCacheDir/small.jpg",
                        externalCacheDir.path
                    )
                    fileBean.newFilePath = selectHeadImg
                    val options = HeadImgUtil.getHeadImgOptions("")
                    Glide.with(this).load(selectHeadImg).apply(options).into(carPhoto)
                    PopUpUtil.showProgressBar(window,progressBar)
                    if (baidu_api_access_token == "" || (Date().time >= baidu_api_expires_in && baidu_api_expires_in != 0.toLong()))
                        baiduAPIPresenterImpl.getToken()
                    else
                        baiduAPIPresenterImpl.getORC(BitmapUtil.compressImage(
                            "$externalCacheDir/small.jpg",
                            externalCacheDir.path
                        ),baidu_api_access_token)
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
    }


    override fun getCarCodeSuccess(baiduOCRResponse: BaiduOCRResponse) {
        Toast.makeText(this,baiduOCRResponse.toString(),Toast.LENGTH_SHORT).show()
        val result = BaiduAPIParese.paraseORCResponse(baiduOCRResponse)
        if (result == null)
            car_code_text.text = "未匹配到车牌号"
        else{
            car_code_text.text = result
            applyButton.visibility = View.VISIBLE
            take_photo.visibility = View.VISIBLE
        }
        PopUpUtil.cancelProgressBar(window,progressBar)
    }

    override fun getCarCodeFailed(errorMessage: String?) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
        PopUpUtil.cancelProgressBar(window,progressBar)
    }

    override fun blindCar(code: String, fileBean: FileBean,accessToken:String) {
        PopUpUtil.showProgressBar(window,progressBar)
        blindPresenterImpl.blindCar(code,fileBean,accessToken)
    }

    override fun blindSuccess(loginResponse: LoginResponse) {
        Toast.makeText(this,"绑定成功",Toast.LENGTH_SHORT).show()
        PopUpUtil.cancelProgressBar(window,progressBar)
        applyButton.visibility = View.INVISIBLE
        take_photo.visibility = View.INVISIBLE
    }

    override fun blindFailed(errorMessage: String?) {
        Toast.makeText(this,"绑定失败",Toast.LENGTH_SHORT).show()
        PopUpUtil.cancelProgressBar(window,progressBar)
    }

    override fun getSelfCarCode(accessToken: String) {
        PopUpUtil.showProgressBar(window,progressBar)
        blindPresenterImpl.getSelfCarCode(accessToken)
    }

    override fun getSelfCarCodeSuccess(selfCarCodeResponse: SelfCarCodeResponse) {
        val ids = selfCarCodeResponse.data?.otherPicIds
        val paths = selfCarCodeResponse.data?.otherPicNames
        if (ids?.size!! > 0 && paths?.size!! > 0)
        {
            fileBean.oldId = ids[0]
            fileBean.filePath = paths[0]
            car_code_text.text = selfCarCodeResponse.data?.photoId.toString()
            val options = RequestOptions()
            options.error(R.drawable.test_bg)
            Glide.with(this).load(fileBean.filePath).apply(options).into(carPhoto)
            applyButton.visibility = View.INVISIBLE
            take_photo.visibility = View.INVISIBLE
        }
        PopUpUtil.cancelProgressBar(window,progressBar)
    }

    override fun getSelfCarCodeFailed(errorMessage: String?) {
        PopUpUtil.cancelProgressBar(window,progressBar)
//        finish()
    }
}
