package com.example.nuonuo.presenter

import com.example.nuonuo.model.MessageModelImpl
import com.example.nuonuo.view.MessageView

class MessagePresenterImpl(private val messageView: MessageView): MessagePresenter.OnMessagePresenterListener {

    private val messageModelImpl: MessageModelImpl = MessageModelImpl()

    override fun getSend() {
        messageModelImpl.getSend(this)
    }

    override fun getSendFailed(errorMessage: String?) {
        messageView.getSendFailed(errorMessage)
    }

    override fun getSentSuccess() {
        messageView.getSentSuccess()
    }

    override fun getRecive() {
        messageModelImpl.getReceive(this)
    }

    override fun getReceiveFailed(errorMessage: String?) {
        messageView.getReceiveFailed(errorMessage)
    }

    override fun getReceiveSuccess() {
        messageView.getReceiveSuccess()
    }
}