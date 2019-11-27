package com.example.nuonuo.model

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.NewTrend
import com.example.nuonuo.bean.NewTrendResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.NewTrendPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
@author yjn
@create 2019/11/27 - 23:27
 */
class NewTrendModelImpl: NewTrendModel {
    private  var newTrendResponseAsyn: Deferred<NewTrendResponse>? = null

    override fun newTrend(
        onNewTrendListerner: NewTrendPresenter.OnNewTrendListerner,
        newTrend: NewTrend,
        accessToken: String
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                newTrendResponseAsyn = RetrofitHelper.retrofitService.newTrend(newTrend,accessToken)
                val result = newTrendResponseAsyn?.await()
                if (result == null){
                    onNewTrendListerner.newTrendFailed(Constant.RESULT_NULL)
                }else
                    onNewTrendListerner.newTrendSuccess(result)

            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onNewTrendListerner.newTrendFailed(e.response().errorBody()?.string())
                }else{
                    onNewTrendListerner.newTrendFailed(e.message)
                }
            }
        }
    }
}