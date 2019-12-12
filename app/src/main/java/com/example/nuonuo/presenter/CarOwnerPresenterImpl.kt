package com.example.nuonuo.presenter

import android.graphics.Bitmap
import com.example.nuonuo.bean.GetPhoneCallResponse
import com.example.nuonuo.bean.PhoneCallBean
import com.example.nuonuo.bean.PhoneCodeResponse
import com.example.nuonuo.model.MessageModelImpl
import com.example.nuonuo.view.CarOwnerView

/**
@author yjn
@create 2019/11/27 - 2:01
 */
class CarOwnerPresenterImpl(private val carOwnerView: CarOwnerView): MessagePresenter.OnSendMessageListener,MessagePresenter.OnGetPhoneCookieAndTokenListener,
    MessagePresenter.OnPhoneCallListener {

    private val messageModelImpl: MessageModelImpl = MessageModelImpl()

    override fun senMessage(content: String, accessToken: String, uid: Int,phone: String) {
        messageModelImpl.sendMessage(this,content,accessToken,uid,phone)
    }

    override fun senMessageeFailed(errorMessage: String?) {
        carOwnerView.senMessageeFailed(errorMessage)
    }

    override fun senMessageSuccess() {
        carOwnerView.senMessageSuccess()
    }

    override fun getPhoneCookieAndToken() {
        messageModelImpl.getPhoneCookieAndToken(this)
    }

    override fun getPhoneCookieAndTokenSuccess(phoneCodeResponse: PhoneCodeResponse) {
        carOwnerView.getCookieAndTokenSuccess(phoneCodeResponse)
    }

    override fun getPhoneCookieAndTokenFailed(errorMessage: String?) {
        carOwnerView.getCookieAndTokenFailed(errorMessage)
    }

    override fun phoneCall(phoneCallBean: PhoneCallBean, accessToken: String) {
        messageModelImpl.phoneCall(this,phoneCallBean,accessToken)
    }

    override fun phoneCallSuccess(phoneCallResponse: GetPhoneCallResponse) {
        carOwnerView.callPhoneSuccess(phoneCallResponse)
    }

    override fun phoneCallFailed(errorMessage: String?) {
        carOwnerView.callPhoneFailed(errorMessage)
    }
}