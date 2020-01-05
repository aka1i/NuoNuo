package com.example.nuonuo.presenter

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.model.LoginModelImpl
import com.example.nuonuo.view.MainView

/**
@author yjn
@create 2020/1/5 - 21:58
 */
class MainViewPresenterImpl(private val mainView: MainView):LoginPresenter.OnGetMyInfoListener {

    private val loginModelImpl = LoginModelImpl()

    override fun getMyInfo(accessToken: String) {
        loginModelImpl.getMyInfo(this,accessToken)
    }

    override fun getMyInfoSuccess(loginResponse: LoginResponse) {
        mainView.getMyInfoSuccess(loginResponse)
    }

    override fun getMyInfoFailed(errorMessage: String?) {
        mainView.getMyInfoFailed(errorMessage)
    }
}