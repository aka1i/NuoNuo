package com.example.nuonuo.bean

/**
@author yjn
@create 2019/12/10 - 11:56
 */
class SelfCarCodeResponse(
    var status: Int?,
    var message: String?,
    var data: Data?
) {
    data class Data(
        var  photoId: String?,
        var otherPicNames:List<String>,
        var otherPicIds:List<Int>
    )
}