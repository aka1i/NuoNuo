package com.example.nuonuo.presenter

import android.net.Uri
import com.example.nuonuo.bean.BaiduOCRResponse
import com.example.nuonuo.bean.BaiduTokenResponse

/**
@author yjn
@create 2019/11/27 - 16:54
 */
interface BiaduAPIPresenter {

    interface OnGetTokenListener{
        fun getToken()

        fun getTokenSuccess(baiduTokenResponse: BaiduTokenResponse)

        fun getTokenFailed(errorMessage: String?)
    }


    interface OnORCListener{
        fun getORC(path: String,access_token:String)

        fun getORCSuccess(baiduOCRResponse: BaiduOCRResponse)

        fun getORCFailed(errorMessage: String?)
    }
}