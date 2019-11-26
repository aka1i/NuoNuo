package com.example.nuonuo.bean

/**
@author yjn
@create 2019/11/26 - 22:32
 */
data class TrendListResponse(
    var status: Int,
    var message: String?,
    var data: List<Data>?
) {
    data class Data(
        var id: Int?,
        var title:String?,
        var content:String?,
        var uid:Int?,
        var name:String?,
        var otherPicDiskNames:String?,
        var otherPicIds:List<Int>?,
        var stateTime:String?,
        var headPicId:String?,
        var headPicUrl:String?
    ){
        data class leave(
            var id: Int?,
            var name:String?,
            var content: String?
        )
    }
}