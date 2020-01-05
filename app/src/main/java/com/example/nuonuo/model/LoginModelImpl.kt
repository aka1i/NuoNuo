package com.example.nuonuo.model

import android.util.Log
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.api.BasicCallback
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.LoginPresenter
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.HttpException

/**
@author yjn
@create 2019/11/12 - 16:10
 */
class LoginModelImpl: LoginModel{
    private  var loginResponseAsyn: Deferred<LoginResponse>? = null
    private  var getMyInfoResponseAsyn: Deferred<LoginResponse>? = null
    override fun login(onLoginListener: LoginPresenter.OnLoginListener,username: String, password: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val jsonObject = JSONObject()
                jsonObject.put("account",username)
                jsonObject.put("password",password)
                JMessageClient.login(username, password, object : BasicCallback(){
                    override fun gotResult(p0: Int, p1: String?) {
                    }
                })
                loginResponseAsyn = RetrofitHelper.retrofitService.login(RetrofitHelper.getRequestBodyByJson(jsonObject))

                val result = loginResponseAsyn?.await()
                if (result == null){
                    onLoginListener.loginFailed(Constant.RESULT_NULL)
                }else
                    onLoginListener.loginSuccess(result)

            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onLoginListener.loginFailed(e.response().errorBody()?.string())
                }else
                    onLoginListener.loginFailed(e.message)
            }
        }
    }

    override fun getMyInfo(onGetMyInfoListener: LoginPresenter.OnGetMyInfoListener, accessToken: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                getMyInfoResponseAsyn = RetrofitHelper.retrofitService.getMyInfo(accessToken)

                val result = getMyInfoResponseAsyn?.await()
                if (result == null){
                    onGetMyInfoListener.getMyInfoFailed(Constant.RESULT_NULL)
                }else
                    onGetMyInfoListener.getMyInfoSuccess(result)

            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onGetMyInfoListener.getMyInfoFailed(e.response().errorBody()?.string())
                }else
                    onGetMyInfoListener.getMyInfoFailed(e.message)
            }
        }
    }
}