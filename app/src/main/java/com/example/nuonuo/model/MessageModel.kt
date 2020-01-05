package com.example.nuonuo.model

import com.example.nuonuo.bean.PhoneCallBean
import com.example.nuonuo.presenter.MessagePresenter

interface MessageModel {
    fun getSend(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int)

    fun getReceive(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int)

    fun sendMessage(onSendMessageListener: MessagePresenter.OnSendMessageListener,content: String, accessToken: String, uid: Int,phone: String)

    fun getPhoneCookieAndToken(onGetPhoneCookieAndTokenListener: MessagePresenter.OnGetPhoneCookieAndTokenListener)

    fun phoneCall(onPhoneCallListener: MessagePresenter.OnPhoneCallListener,phoneCallBean: PhoneCallBean,accessToken: String)

    fun dianzan(onDianzanListener: MessagePresenter.OnDianzanListener,id:Int,accessToken: String)

}