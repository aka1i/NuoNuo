package com.example.nuonuo.bean

/**
@author yjn
@create 2019/11/27 - 23:12
 */
data class NewTrendResponse(
    var status: Int?,
    var message: String?,
    var data: Data?
) {
    data class Data(
        var id:Int?,
        var uid:Int?,
        var title:String?,
        var content:String?,
        var stateTime:String?,
        var otherPicIds: List<Int>
    )
}