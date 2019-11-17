package com.example.nuonuo.bean

import retrofit2.http.Url

data class TrendBean (
    var name:String?,
    var time:String?,
    var headImgUrl: String?,
    var content: String?,
    var contentNum: Int?,
    var shareNum: Int?,
    var thumbNum: Int?,
    var isThumbed: Boolean?,
    var imgsUrl: List<String>?,
    var leaves: List<leave>?
){
    data class leave(var leaveName: String?,var leaveContent: String?)
}