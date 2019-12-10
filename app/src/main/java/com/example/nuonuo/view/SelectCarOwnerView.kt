package com.example.nuonuo.view

import com.example.nuonuo.base.ErrorCallBackView
import com.example.nuonuo.bean.GetCarOnwerResponse

/**
@author yjn
@create 2019/12/10 - 17:40
 */
interface SelectCarOwnerView: ErrorCallBackView {

    fun getCarOwnerList(id: String)

    fun getCarOwnerListSuccess(getCarOnwerResponse: GetCarOnwerResponse)

    fun getCarOwnerListFailed(errorString: String?)

}