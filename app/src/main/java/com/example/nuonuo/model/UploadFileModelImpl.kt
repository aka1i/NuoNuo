package com.example.nuonuo.model

import android.util.Log
import com.example.nuonuo.bean.UploadFileResponse
import com.example.nuonuo.marco.Constant
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
@author yjn
@create 2019/11/28 - 16:00
 */
class UploadFileModelImpl: UploadFileModel {
    companion object{
        const val UPLOAD_IMAGE = 0
    }

    private  var uploadFileResponseAsyn: Deferred<UploadFileResponse>? = null

    override fun uploadFile(filePath: String, accessToken: String, type: Int):Deferred<UploadFileResponse> = GlobalScope.async(Dispatchers.Main) {
        try {
            uploadFileResponseAsyn = RetrofitHelper.retrofitService.uploadFile(RetrofitHelper.getImageBody(filePath),accessToken)
            uploadFileResponseAsyn?.await() ?: UploadFileResponse(400,Constant.RESULT_NULL,null)
        }catch (e: Exception){
            e.printStackTrace()
            if(e is HttpException){
                UploadFileResponse(400,e.response().errorBody()?.string(),null)
            }else{
                UploadFileResponse(400,e.message,null)
            }
        }
    }

}