package com.example.nuonuo.bean

/**
@author yjn
@create 2019/11/28 - 15:56
 */
data class UploadFileResponse(
    var status: Int?,
    var message: String?,
    var data: Data?
){
    data class Data(
        var id: Int,
        var url: String,
        var name: String,
        var size: Long
    )
}