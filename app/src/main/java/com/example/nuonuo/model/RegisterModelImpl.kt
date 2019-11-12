package com.example.nuonuo.model

import com.example.nuonuo.bean.RegisterResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.RegisterPresenter
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.HttpException


/**
@author yjn
@create 2019/11/10 - 19:17
 */
class RegisterModelImpl: RegisterModel{
    private  var registerResponseAsyn: Deferred<RegisterResponse>? = null
    override fun register(
        onRegisterListener: RegisterPresenter.OnRegisterListener,
        username: String,
        password: String,
        email: String
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
            val jsonObject = JSONObject()
            jsonObject.put("phone",username)
            jsonObject.put("password",password)
            jsonObject.put("email",email)
            registerResponseAsyn = RetrofitHelper.retrofitService.register(RetrofitHelper.getRequestBodyByJson(jsonObject))
            val result = registerResponseAsyn?.await()
            if (result == null){
                onRegisterListener.registerFailed(Constant.RESULT_NULL)
            }else
                onRegisterListener.registerSuccess(result)

//            val result = null
        }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onRegisterListener.registerFailed(e.response().errorBody()?.string())
                }
            }
        }

    }

}