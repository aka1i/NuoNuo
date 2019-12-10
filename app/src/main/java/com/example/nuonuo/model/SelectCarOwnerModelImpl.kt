package com.example.nuonuo.model

import com.example.nuonuo.bean.GetCarOnwerResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.SelectCarOwnerPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
@author yjn
@create 2019/12/10 - 17:47
 */
class SelectCarOwnerModelImpl: SelectCarOwnerModel {
    private  var listResponseAsyn: Deferred<GetCarOnwerResponse>? = null
    override fun getOwnerList(onSelectCarOwner: SelectCarOwnerPresenter.OnSelectCarOwner, id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                listResponseAsyn = RetrofitHelper.retrofitService.getOwnerList(id)
                val result = listResponseAsyn?.await()
                if (result == null){
                    onSelectCarOwner.getCarOwnerListFailed(Constant.RESULT_NULL)
                }else{
                    onSelectCarOwner.getCarOwnerListSuccess(result)
                }
            }catch (e: Exception){
                if(e is HttpException){
                    onSelectCarOwner.getCarOwnerListFailed(e.response().errorBody()?.string())
                }else{
                    onSelectCarOwner.getCarOwnerListFailed(e.message)
                }
            }
        }
    }
}