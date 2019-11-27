package com.example.nuonuo.view

interface MessageListView {
    fun getSendFailed(errorMessage: String?)

    fun getSentSuccess()

    fun getReceiveFailed(errorMessage: String?)

    fun  getReceiveSuccess()

    fun stopRefresh()
}