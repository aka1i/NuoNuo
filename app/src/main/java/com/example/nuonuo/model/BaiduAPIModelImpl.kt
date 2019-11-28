package com.example.nuonuo.model

import android.util.Log
import com.example.nuonuo.bean.BaiduOCRResponse
import com.example.nuonuo.bean.BaiduTokenResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.BaiduAPIPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.example.nuonuo.utils.baidu.Base64Util
import com.example.nuonuo.utils.baidu.FileUtil


/**
@author yjn
@create 2019/11/27 - 16:51
 */
class BaiduAPIModelImpl: BaiduAPIModel{
    private  var baiduTokenResponseAsyn: Deferred<BaiduTokenResponse>? = null
    private  var baiduOCRResponseAsyn: Deferred<BaiduOCRResponse>? = null
    override fun getToken(onGetTokenListener: BaiduAPIPresenter.OnGetTokenListener) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                baiduTokenResponseAsyn = RetrofitHelper.retrofitService.getBaiduToken()
                val result = baiduTokenResponseAsyn?.await()
                if (result == null){
                    onGetTokenListener.getTokenFailed(Constant.RESULT_NULL)
                }else
                {
                    onGetTokenListener.getTokenSuccess(result)
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onGetTokenListener.getTokenFailed(e.response().errorBody()?.string())
                }else{
                    onGetTokenListener.getTokenFailed(e.message)
                }
            }
        }
    }


        override fun getORC(onORCListener: BaiduAPIPresenter.OnORCListener, path: String, access_token:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val imgData = FileUtil.readFileByBytes(path)
                val imgStr = Base64Util.encode(imgData)
//                val imgParam =  URLEncoder.encode(imgStr, "UTF-8")
                Log.d("GETORC",imgStr)
                baiduOCRResponseAsyn = RetrofitHelper.retrofitService.getORC(
                    imgStr,
                    access_token)
                val result = baiduOCRResponseAsyn?.await()
                if (result == null){
                    onORCListener.getORCFailed(Constant.RESULT_NULL)
                }else
                {
                    onORCListener.getORCSuccess(result)
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onORCListener.getORCFailed(e.response().errorBody()?.string())
                }else{
                    onORCListener.getORCFailed(e.message)
                }
            }
        }
    }
}