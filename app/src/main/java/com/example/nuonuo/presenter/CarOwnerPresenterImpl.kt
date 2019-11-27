package com.example.nuonuo.presenter

import com.example.nuonuo.model.MessageModelImpl
import com.example.nuonuo.view.CarOwnerView

/**
@author yjn
@create 2019/11/27 - 2:01
 */
class CarOwnerPresenterImpl(private val carOwnerView: CarOwnerView): MessagePresenter.OnSendMessageListener {

    private val messageModelImpl: MessageModelImpl = MessageModelImpl()

    override fun senMessage(content: String, accessToken: String, uid: Int) {
        messageModelImpl.sendMessage(this,content,accessToken,uid)
    }

    override fun senMessageeFailed(errorMessage: String?) {
        carOwnerView.senMessageeFailed(errorMessage)
    }

    override fun senMessageSuccess() {
        carOwnerView.senMessageSuccess()
    }
}