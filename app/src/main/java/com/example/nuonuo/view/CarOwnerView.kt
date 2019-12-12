package com.example.nuonuo.view

import android.graphics.Bitmap
import com.example.nuonuo.bean.GetPhoneCallResponse
import com.example.nuonuo.bean.PhoneCodeResponse

/**
@author yjn
@create 2019/11/27 - 1:59
 */
interface CarOwnerView {
    fun senMessage(content:String,accessToken:String,uid: Int,phone: String)

    fun senMessageeFailed(errorMessage: String?)

    fun  senMessageSuccess()

    fun callPhone(code: String)

    fun callPhoneSuccess(getPhoneCallResponse: GetPhoneCallResponse)

    fun callPhoneFailed(errorMessage:String?)

    fun getCookieAndToken()

    fun getCookieAndTokenSuccess(phoneCodeResponse: PhoneCodeResponse)

    fun getCookieAndTokenFailed(errorMessage:String?)

}