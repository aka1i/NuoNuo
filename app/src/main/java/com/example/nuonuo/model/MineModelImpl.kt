package com.example.nuonuo.model

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.UserInfo
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.MinePresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

/**
@author yjn
@create 2019/11/26 - 20:50
 */
class MineModelImpl: MineModel {
    private  var modifyResponseAsyn: Deferred<LoginResponse>? = null
    override fun modifyUserInfo(modifyListener: MinePresenter.OnModifyListener,userInfo: UserInfo,accessToken: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                modifyResponseAsyn = RetrofitHelper.retrofitService.modifyUserInfo(userInfo,accessToken)
                val result = modifyResponseAsyn?.await()
                if (result == null){
                    modifyListener.modifyFailed(Constant.RESULT_NULL)
                }else
                    modifyListener.modifySuccess(result)

            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    modifyListener.modifyFailed(e.response().errorBody()?.string())
                }else{
                    modifyListener.modifyFailed(e.message)
                }
            }
        }
    }
}