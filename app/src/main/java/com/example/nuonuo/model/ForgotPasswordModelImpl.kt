package com.example.nuonuo.model

import android.widget.Toast
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.ForgotPasswordPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class ForgotPasswordModelImpl(private val onForgotPasswordListener: ForgotPasswordPresenter.OnForgotPasswordListener): ForgotPasswordModel {
    private  var codeResponseAsyn: Deferred<LoginResponse>? = null

    private  var forgotPasswordResponseAsyn: Deferred<LoginResponse>? = null

    override fun getEamilCode(email: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {

                codeResponseAsyn = RetrofitHelper.retrofitService.getForgotPasswordCode("http://47.101.140.66:90/mail/sendResetEmail/" + email)
                val result = codeResponseAsyn?.await()
                if (result == null){
                    onForgotPasswordListener.getEmailFailed(Constant.RESULT_NULL)
                }else
                    onForgotPasswordListener.getEmailSuccess(result)

            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onForgotPasswordListener.getEmailFailed(e.response().errorBody()?.string())
                }
            }
        }
    }

    override fun forgotPassword(email: String, password: String, code: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val jsonObject = JSONObject()
                jsonObject.put("email",email)
                jsonObject.put("password",password)
                jsonObject.put("code",code)
                forgotPasswordResponseAsyn = RetrofitHelper.retrofitService.forgotPassword(RetrofitHelper.getRequestBodyByJson(jsonObject))
                val result = forgotPasswordResponseAsyn?.await()
                if (result == null){
                    onForgotPasswordListener.forgotPasswordFailed(Constant.RESULT_NULL)
                }else
                    onForgotPasswordListener.forgotPasswordSuccess(result)

            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onForgotPasswordListener.forgotPasswordFailed(e.response().errorBody()?.string())
                }
            }
        }
    }
}