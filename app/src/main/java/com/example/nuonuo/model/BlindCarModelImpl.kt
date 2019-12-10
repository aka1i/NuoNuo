package com.example.nuonuo.model

import com.example.nuonuo.bean.FileBean
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.SelfCarCodeResponse
import com.example.nuonuo.bean.UploadFileResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.BlindCarPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException

/**
@author yjn
@create 2019/12/10 - 11:38
 */
class BlindCarModelImpl: BlindCarModel {
    private  var blindCarResponseAsyn: Deferred<LoginResponse>? = null
    private  var getSelfCarCodeResponseAsyn: Deferred<SelfCarCodeResponse>? = null

    private var imgResponse: UploadFileResponse? = null
    private val uploadFileModelImpl: UploadFileModelImpl = UploadFileModelImpl()

    override fun blindCar(onBlindCarListener: BlindCarPresenter.OnBlindCarListener,code: String, fileBean: FileBean,accessToken:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {

                fileBean.newFilePath?.run {
                    imgResponse =
                        uploadFileModelImpl.uploadFile(this,accessToken,UploadFileModelImpl.UPLOAD_IMAGE).await()
                            .run {
                                if (this.status != 0)
                                    throw Exception(this.message)
                                fileBean.newId = this.data?.id
                                this
                            }
                }
                val json = JSONObject()
                val jsonArray = JSONArray()
                jsonArray.put(fileBean.newId)
                json.put("photoId",code)
                json.put("imageIds",jsonArray)
                blindCarResponseAsyn = RetrofitHelper.retrofitService.blindCode(RetrofitHelper.getRequestBodyByJson(json),accessToken)
                val result = blindCarResponseAsyn?.await()
                if (result == null){
                    onBlindCarListener.blindFailed(Constant.RESULT_NULL)
                }else
                    onBlindCarListener.blindSuccess(result)

            }catch (e: Exception){
                if(e is HttpException){
                    onBlindCarListener.blindFailed(e.response().errorBody()?.string())
                }else{
                    onBlindCarListener.blindFailed(e.message)
                }
            }
        }
    }


    override fun getSelfCarCode(
        onGetSelfCarCode: BlindCarPresenter.OnGetSelfCarCode,
        accessToken: String
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {

                getSelfCarCodeResponseAsyn = RetrofitHelper.retrofitService.getSelfCarCode(accessToken)
                val result = getSelfCarCodeResponseAsyn?.await()
                if (result == null){
                    onGetSelfCarCode.getSelfCarCodeFailed(Constant.RESULT_NULL)
                }else
                    onGetSelfCarCode.getSelfCarCodeSuccess(result)

            }catch (e: Exception){
                if(e is HttpException){
                    onGetSelfCarCode.getSelfCarCodeFailed(e.response().errorBody()?.string())
                }else{
                    onGetSelfCarCode.getSelfCarCodeFailed(e.message)
                }
            }
        }
    }
}