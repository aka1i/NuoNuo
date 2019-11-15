package com.example.nuonuo.view

interface MessageView {
    fun getSendFailed(errorMessage: String?)

    fun getSentSuccess()

    fun getReceiveFailed(errorMessage: String?)

    fun  getReceiveSuccess()
}