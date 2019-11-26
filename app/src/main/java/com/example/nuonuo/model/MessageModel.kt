package com.example.nuonuo.model

import com.example.nuonuo.presenter.MessagePresenter

interface MessageModel {
    fun getSend(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int)

    fun getReceive(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int)
}