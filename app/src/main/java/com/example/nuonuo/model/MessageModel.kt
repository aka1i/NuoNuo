package com.example.nuonuo.model

import com.example.nuonuo.presenter.MessagePresenter

interface MessageModel {
    fun getSend(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int)

    fun getReceive(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int)

    fun sendMessage(onSendMessageListener: MessagePresenter.OnSendMessageListener,content: String, accessToken: String, uid: Int,phone: String)
}