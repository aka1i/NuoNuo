package com.example.nuonuo.model

import com.example.nuonuo.bean.MessageItemBean
import com.example.nuonuo.presenter.MessagePresenter

interface MessageModel {
    fun getSend(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener)

    fun getReceive(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener)
}