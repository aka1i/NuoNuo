package com.example.nuonuo.presenter

import com.example.nuonuo.bean.GetCarOnwerResponse

/**
@author yjn
@create 2019/12/10 - 17:44
 */
interface SelectCarOwnerPresenter {

    interface OnSelectCarOwner{
        fun getCarOwnerList(id: String)

        fun getCarOwnerListSuccess(getCarOnwerResponse: GetCarOnwerResponse)

        fun getCarOwnerListFailed(errorString: String?)
    }

}