package com.example.nuonuo.bean

/**
@author yjn
@create 2019/11/27 - 1:26
 */
data class SendMessageResponse(
    var status: Int,
    var message: String?,
    val data: Data
) {
    data class Data(
        var id: Int?,
        var sendId: Int?,
        var getId: Int?,
        var content: String?
    )

}