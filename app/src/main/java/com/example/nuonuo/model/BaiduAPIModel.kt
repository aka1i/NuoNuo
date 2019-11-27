package com.example.nuonuo.model

import com.example.nuonuo.presenter.BiaduAPIPresenter

/**
@author yjn
@create 2019/11/27 - 16:50
 */
interface BaiduAPIModel {
    fun getToken(onGetTokenListener: BiaduAPIPresenter.OnGetTokenListener)

    fun getORC(onORCListener: BiaduAPIPresenter.OnORCListener,path: String,access_token:String)
}