package com.example.nuonuo.bean

import com.example.nuonuo.bean.MessageListResponse.MessageItemBean

object MessageLab {
    var sendMessages: MutableList<MessageItemBean> = mutableListOf()
    var receiveMessage: MutableList<MessageItemBean> = mutableListOf()
}