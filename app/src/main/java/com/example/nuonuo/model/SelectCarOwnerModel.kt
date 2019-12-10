package com.example.nuonuo.model

import com.example.nuonuo.presenter.SelectCarOwnerPresenter

/**
@author yjn
@create 2019/12/10 - 17:46
 */
interface SelectCarOwnerModel {

    fun getOwnerList(onSelectCarOwner: SelectCarOwnerPresenter.OnSelectCarOwner,id: String)

}