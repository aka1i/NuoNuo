package com.example.nuonuo.presenter

import android.graphics.Bitmap
import com.example.nuonuo.bean.GetPhoneCallResponse
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.PhoneCallBean
import com.example.nuonuo.bean.PhoneCodeResponse

interface MessagePresenter {
    interface OnMessagePresenterListener{
        fun getSend(accessToken:String,uid: Int)

        fun getSendFailed(errorMessage: String?)

        fun getSentSuccess()

        fun getRecive(accessToken:String,uid: Int)

        fun getReceiveFailed(errorMessage: String?)

        fun  getReceiveSuccess()

    }

    interface OnSendMessageListener{
        fun senMessage(content:String,accessToken:String,uid: Int,phone: String)

        fun senMessageeFailed(errorMessage: String?)

        fun  senMessageSuccess()
    }

    interface OnGetPhoneCookieAndTokenListener{
        fun getPhoneCookieAndToken()

        fun getPhoneCookieAndTokenSuccess(phoneCodeResponse:PhoneCodeResponse)

        fun getPhoneCookieAndTokenFailed(errorMessage: String?)
    }

    interface OnPhoneCallListener{
        fun phoneCall(phoneCallBean: PhoneCallBean,accessToken: String)

        fun phoneCallSuccess(phoneCallResponse: GetPhoneCallResponse)

        fun phoneCallFailed(errorMessage: String?)
    }

    interface OnDianzanListener{

        fun dianzan(id:Int, accessToken: String)

        fun dianzanSuccess(loginResponse: LoginResponse)

        fun dianzanFailed(errorMessage: String?)

    }

}