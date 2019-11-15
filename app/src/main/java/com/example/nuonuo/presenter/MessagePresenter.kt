package com.example.nuonuo.presenter

import com.example.nuonuo.view.MessageView

interface MessagePresenter {
    interface OnMessagePresenterListener{
        fun getSend()

        fun getSendFailed(errorMessage: String?)

        fun getSentSuccess()

        fun getRecive()

        fun getReceiveFailed(errorMessage: String?)

        fun  getReceiveSuccess()
    }
}