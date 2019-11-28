package com.example.nuonuo.model

import com.example.nuonuo.presenter.BaiduAPIPresenter

/**
@author yjn
@create 2019/11/27 - 16:50
 */
interface BaiduAPIModel {
    fun getToken(onGetTokenListener: BaiduAPIPresenter.OnGetTokenListener)

    fun getORC(onORCListener: BaiduAPIPresenter.OnORCListener, path: String, access_token:String)
}