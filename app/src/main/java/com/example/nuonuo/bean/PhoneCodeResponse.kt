package com.example.nuonuo.bean

/**
@author yjn
@create 2019/12/12 - 11:26
 */

data class PhoneCodeResponse(
    var formToken:String,
    var cookie: CookieData
)

data class PhoneCallBean(
    var callId:String?,
    var code:String?,
    var formToken:String?,
    var cookie: CookieData?
)
data class CookieData(
    var PHPSESSID:String
)

data class GetPhoneCallResponse(
    var status: Int?,
    var message: String?,
    var data: String?
)