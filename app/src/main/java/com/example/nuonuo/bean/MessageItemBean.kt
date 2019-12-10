package com.example.nuonuo.bean


data class MessageListResponse(
    var status: Int,
    var message: String?,
    val data: List<MessageItemBean>?
){
    data class MessageItemBean(
        var id: Int?,
        var sendId: Int?,
        var getId: Int?,
        var content: String,
        var sendName:String,
        var getName:String,
        var stateTime: String,
        var headPicUrl: String,
        var headPicId: Int?
    )
}
