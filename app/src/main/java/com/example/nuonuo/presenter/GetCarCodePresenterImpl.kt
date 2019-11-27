package com.example.nuonuo.presenter

import com.example.nuonuo.bean.BaiduOCRResponse
import com.example.nuonuo.bean.BaiduTokenResponse
import com.example.nuonuo.model.BaiduAPIModelImpl
import com.example.nuonuo.view.GetCarCodeView

/**
@author yjn
@create 2019/11/27 - 16:55
 */
class GetCarCodePresenterImpl(private val baiduAPIView: GetCarCodeView): BiaduAPIPresenter.OnGetTokenListener,BiaduAPIPresenter.OnORCListener {

    private val baiduAPIModelImpl: BaiduAPIModelImpl = BaiduAPIModelImpl()

    override fun getToken() {
        baiduAPIModelImpl.getToken(this)
    }

    override fun getTokenSuccess(baiduTokenResponse: BaiduTokenResponse) {
        baiduAPIView.getTokenSuccess(baiduTokenResponse)
    }

    override fun getTokenFailed(errorMessage: String?) {
        baiduAPIView.getTokenFailed(errorMessage)
    }


    override fun getORC(path: String,access_token:String) {
        baiduAPIModelImpl.getORC(this,path,access_token)
    }

    override fun getORCSuccess(baiduOCRResponse: BaiduOCRResponse) {
        baiduAPIView.getCarCodeSuccess(baiduOCRResponse)
    }

    override fun getORCFailed(errorMessage: String?) {
        baiduAPIView.getTokenFailed(errorMessage)
    }
}