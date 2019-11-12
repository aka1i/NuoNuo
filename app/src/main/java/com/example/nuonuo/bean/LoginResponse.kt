package com.example.nuonuo.bean

/**
@author yjn
@create 2019/11/12 - 16:00
 */
data class LoginResponse(
    var status: Int,
    var message: String?,
    val data: Data
) {
    data class Data(
        var uid: Int,
        var accessToken: String,
        var phone: String,
        var name: String,
        var sexual: String,
        var qq: String?,
        var weixin: String?,
        var headPicId: Int?,
        var headPicUrl: String?
    )

}