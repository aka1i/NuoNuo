package com.example.nuonuo.presenter

import com.example.nuonuo.bean.GetCarOnwerResponse
import com.example.nuonuo.model.SelectCarOwnerModel
import com.example.nuonuo.model.SelectCarOwnerModelImpl
import com.example.nuonuo.view.SelectCarOwnerView

/**
@author yjn
@create 2019/12/10 - 17:45
 */
class SelectCarOwnerPresenterImpl(private val selectCarOwnerView: SelectCarOwnerView): SelectCarOwnerPresenter.OnSelectCarOwner{

    private val selectCarOwnerModelImpl = SelectCarOwnerModelImpl()

    override fun getCarOwnerList(id: String) {
        selectCarOwnerModelImpl.getOwnerList(this,id)
    }

    override fun getCarOwnerListSuccess(getCarOnwerResponse: GetCarOnwerResponse) {
        selectCarOwnerView.getCarOwnerListSuccess(getCarOnwerResponse)
    }

    override fun getCarOwnerListFailed(errorString: String?) {
        selectCarOwnerView.getCarOwnerListFailed(errorString)
    }
}