package com.example.nuonuo.presenter

import com.example.nuonuo.model.MessageModelImpl
import com.example.nuonuo.view.MessageView

class MessagePresenterImpl(private val messageView: MessageView): MessagePresenter.OnMessagePresenterListener {

    private val messageModelImpl: MessageModelImpl = MessageModelImpl()

    override fun getSend(accessToken:String,uid: Int) {
        messageModelImpl.getSend(this,accessToken,uid)
    }

    override fun getSendFailed(errorMessage: String?) {
        messageView.getSendFailed(errorMessage)
    }

    override fun getSentSuccess() {
        messageView.getSentSuccess()
    }

    override fun getRecive(accessToken:String,uid: Int) {
        messageModelImpl.getReceive(this,accessToken,uid)
    }

    override fun getReceiveFailed(errorMessage: String?) {
        messageView.getReceiveFailed(errorMessage)
    }

    override fun getReceiveSuccess() {
        messageView.getReceiveSuccess()
    }
}