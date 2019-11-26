package com.example.nuonuo.presenter

import com.example.nuonuo.view.MessageView

interface MessagePresenter {
    interface OnMessagePresenterListener{
        fun getSend(accessToken:String,uid: Int)

        fun getSendFailed(errorMessage: String?)

        fun getSentSuccess()

        fun getRecive(accessToken:String,uid: Int)

        fun getReceiveFailed(errorMessage: String?)

        fun  getReceiveSuccess()
    }
}