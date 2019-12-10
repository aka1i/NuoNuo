package com.example.nuonuo.ui.fragment


import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide

import com.example.nuonuo.R
import com.example.nuonuo.marco.Constant.CHOOSE_PHOTO
import com.example.nuonuo.marco.Constant.CROP_REQUEST_CODE
import com.example.nuonuo.marco.Constant.TAKE_PHOTO
import com.example.nuonuo.ui.activity.GetCarCodeActivity
import com.example.nuonuo.utils.HeadImgUtil
import com.example.nuonuo.utils.PopUpUtil
import com.example.nuonuo.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_mine_setting.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_bottom_dialog.view.*
import java.io.File
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener{

    private lateinit var cameraUri: Uri
    private lateinit var cropUri: Uri
    private var pop: PopupWindow? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){

        home_camera_cv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.home_camera_cv ->{
                showImagePop()
            }
        }
    }

    private fun showImagePop() {
        activity?.run {
            val bottomView = View.inflate(activity, R.layout.layout_bottom_dialog, null)
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
                            ImageUtil.openAlbum(this@HomeFragment)
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
                            cameraUri = ImageUtil.getPhotoByCamera(this@HomeFragment)!!
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

            }
            bottomView.tv_album.setOnClickListener(clickListener)
            bottomView.tv_camera.setOnClickListener(clickListener)
            bottomView.tv_cancel.setOnClickListener(clickListener)
        }

    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activity?.run {
            when (requestCode) {
                TAKE_PHOTO -> if (resultCode == RESULT_OK) {
                    try {
                        cropUri = ImageUtil.cropPhotoForRectangle(this@HomeFragment,cameraUri,3,4)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                CHOOSE_PHOTO -> {// 图片选择结果回调
                    if (resultCode == RESULT_OK) {
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
                            cropUri = ImageUtil.cropPhotoForRectangle(this@HomeFragment,cropUri,3,4)
                        }
                    }
                }
                CROP_REQUEST_CODE ->{
                    if (resultCode == RESULT_OK) {
                        val intent = Intent(this,GetCarCodeActivity::class.java)
                        startActivity(intent)
                    }else{
                    }
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
                Toast.makeText(activity, "您已拒绝读取SD卡权限，请前往设置授权该应用", Toast.LENGTH_SHORT).show()
            }
            2 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraUri = ImageUtil.getPhotoByCamera(this)!!
            } else {
                Toast.makeText(activity, "您已拒绝读拍照，请前往设置授权该应用", Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }
}
