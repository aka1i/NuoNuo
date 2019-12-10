package com.example.nuonuo.model

import com.example.nuonuo.bean.TrendLab
import com.example.nuonuo.bean.TrendListResponse
import com.example.nuonuo.bean.UploadFileResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.TrendPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TrendListModelImpl: TrendListModel {
    private  var trendListResponseAsyn: Deferred<TrendListResponse>? = null

    override fun getTrendList(trendListDataListener: TrendPresenter.OnGetTrendListDataListener) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                trendListResponseAsyn = RetrofitHelper.retrofitService.getTrendList()
                val result = trendListResponseAsyn?.await()
                if (result == null){
                    trendListDataListener.getTrendListDataFailed(Constant.RESULT_NULL)
                }else{
                    result.data?.run {
                        TrendLab.datas = this
                    }

                    trendListDataListener.getTrendListDataSuccess()
                }


            }catch (e: Exception){
                if(e is HttpException){
                    trendListDataListener.getTrendListDataFailed(e.response().errorBody()?.string())
                }else{
                    trendListDataListener.getTrendListDataFailed(e.message)
                }
            }
        }
    }
}