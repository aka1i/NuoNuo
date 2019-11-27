package com.example.nuonuo.view

import com.example.nuonuo.bean.BaiduOCRResponse
import com.example.nuonuo.bean.BaiduTokenResponse

/**
@author yjn
@create 2019/11/27 - 16:58
 */
interface GetCarCodeView {

    fun getTokenSuccess(baiduTokenResponse: BaiduTokenResponse)

    fun getTokenFailed(errorMessage: String?)

    fun getCarCodeSuccess(baiduOCRResponse: BaiduOCRResponse)

    fun getCarCodeFailed(errorMessage: String?)

}