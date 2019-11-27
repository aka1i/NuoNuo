package com.example.nuonuo.presenter

interface MessagePresenter {
    interface OnMessagePresenterListener{
        fun getSend(accessToken:String,uid: Int)

        fun getSendFailed(errorMessage: String?)

        fun getSentSuccess()

        fun getRecive(accessToken:String,uid: Int)

        fun getReceiveFailed(errorMessage: String?)

        fun  getReceiveSuccess()

    }

    interface OnSendMessageListener{
        fun senMessage(content:String,accessToken:String,uid: Int)

        fun senMessageeFailed(errorMessage: String?)

        fun  senMessageSuccess()
    }
}