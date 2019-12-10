package com.example.nuonuo.bean

/**
@author yjn
@create 2019/12/10 - 17:29
 */
class GetCarOnwerResponse(
    var status: Int?,
    var message: String?,
    var data: List<Data>?
) {
    data class Data(
        var  photoId: String?,
        var otherPicNames:List<String>,
        var otherPicIds:List<Int>,
        var phone:String,
        var uid: Int,
        var headPicUrl: String?,
        var name: String
    )
}