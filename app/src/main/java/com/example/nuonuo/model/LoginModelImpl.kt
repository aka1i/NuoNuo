package com.example.nuonuo.model

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.LoginPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

/**
@author yjn
@create 2019/11/12 - 16:10
 */
class LoginModelImpl: LoginModel{
    private  var loginResponseAsyn: Deferred<LoginResponse>? = null

    override fun login(onLoginListener: LoginPresenter.OnLoginListener,username: String, password: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val jsonObject = JSONObject()
                jsonObject.put("account",username)
                jsonObject.put("password",password)
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
                }
            }
        }
    }
}