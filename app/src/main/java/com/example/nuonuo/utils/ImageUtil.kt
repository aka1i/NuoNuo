package com.example.nuonuo.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.nuonuo.marco.Constant.CHOOSE_PHOTO
import com.example.nuonuo.marco.Constant.CROP_REQUEST_CODE
import com.example.nuonuo.marco.Constant.TAKE_PHOTO
import java.io.File
import java.io.IOException

/**
@author yjn
@create 2019/11/25 - 19:32
 */
class ImageUtil {
    companion object{


        fun getPhotoByCamera(activity: Activity):Uri {
            var cameraUri: Uri
            activity.run {
                val outputImage = File(externalCacheDir, "output.jpg")
                try {
                    if (outputImage.exists())
                        outputImage.delete()
                    outputImage.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    cameraUri = FileProvider.getUriForFile(
                        this,
                        "com.example.nuonuo.provider", outputImage
                    )
                } else {
                    cameraUri = Uri.fromFile(outputImage)
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
                startActivityForResult(intent, TAKE_PHOTO)
            }
            return cameraUri
        }

        fun getPhotoByCamera(fragment: Fragment):Uri? {
            var cameraUri: Uri? = null
            fragment.activity?.run {
                val outputImage = File(externalCacheDir, "output.jpg")
                try {
                    if (outputImage.exists())
                        outputImage.delete()
                    outputImage.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    cameraUri = FileProvider.getUriForFile(
                        this,
                        "com.example.nuonuo.provider", outputImage
                    )
                } else {
                    cameraUri = Uri.fromFile(outputImage)
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
                fragment.startActivityForResult(intent, TAKE_PHOTO)
            }
            return cameraUri
        }



        fun openAlbum(fragment: Fragment) {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            fragment.startActivityForResult(intent, CHOOSE_PHOTO)
        }

        fun openAlbum(activity: Activity) {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            activity.startActivityForResult(intent, CHOOSE_PHOTO)
        }

        @TargetApi(19)
        fun handleImageOnKitKat(context: Context,uri: Uri): String? {
            var imagePath: String? = null
            if (DocumentsContract.isDocumentUri(context, uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                if ("com.android.providers.media.documents" == uri.authority) {
                    val id =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    val selection = MediaStore.Images.Media._ID + "=" + id
                    imagePath =
                        getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
                } else if ("com.android.providers.downloads.documents" == uri.authority) {
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(docId)
                    )
                    imagePath = getImagePath(context,contentUri, null)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                imagePath = getImagePath(context,uri, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                imagePath = uri.path
            }
            return imagePath
        }


        fun handleImageBeforeKitKat(context: Context,uri: Uri): String? {
            return getImagePath(context,uri, null)
        }

        private fun getImagePath(context: Context,uri: Uri, selection: String?): String? {
            var path: String? = null
            val cursor = context.contentResolver.query(uri, null, selection, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                }
                cursor.close()
            }
            return path
        }

        //裁剪
        fun cropPhotoForCircle(context: Activity, uri: Uri): Uri {
            val intent = Intent("com.android.camera.action.CROP")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("crop", "true")
            intent.putExtra("circleCrop", "true")
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)

            intent.putExtra("outputX", 300)
            intent.putExtra("outputY", 300)
            //intent.putExtra("return-data", true);小米系统不支持

            val cropUri = Uri.parse("file://" + "/" + context.externalCacheDir + "/" + "small.jpg")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)
            context.startActivityForResult(intent, CROP_REQUEST_CODE)
            return cropUri
        }

        fun cropPhotoForCircle(context: Fragment, uri: Uri): Uri {
            val intent = Intent("com.android.camera.action.CROP")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("crop", "true")
            intent.putExtra("circleCrop", "true")
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)

            intent.putExtra("outputX", 300)
            intent.putExtra("outputY", 300)
            //intent.putExtra("return-data", true);小米系统不支持

            val cropUri = Uri.parse("file://" + "/" + context.activity?.externalCacheDir + "/" + "small.jpg")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)
            context.startActivityForResult(intent, CROP_REQUEST_CODE)
            return cropUri
        }
        fun cropPhotoForRectangle(context: Fragment, uri: Uri,width: Int,height: Int): Uri{
            val intent = Intent("com.android.camera.action.CROP")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("aspectX", width)
            intent.putExtra("aspectY", height)
            intent.putExtra("outputX", width * 100)
            intent.putExtra("outputY", height * 100)
            //intent.putExtra("return-data", true);小米系统不支持

            val cropUri = Uri.parse("file://" + "/" + context.activity?.externalCacheDir + "/" + "small.jpg")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)

            context.startActivityForResult(intent, CROP_REQUEST_CODE)
            return cropUri
        }

        fun cropPhotoForRectangle(context: Activity, uri: Uri,width: Int,height: Int): Uri{
            val intent = Intent("com.android.camera.action.CROP")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("aspectX", width)
            intent.putExtra("aspectY", height)
            intent.putExtra("outputX", width * 100)
            intent.putExtra("outputY", height * 100)
            //intent.putExtra("return-data", true);小米系统不支持

            val cropUri = Uri.parse("file://" + "/" + context.externalCacheDir + "/" + "small.jpg")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)

            context.startActivityForResult(intent, CROP_REQUEST_CODE)
            return cropUri
        }


    }
}