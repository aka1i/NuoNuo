package com.example.nuonuo.presenter

import com.example.nuonuo.model.TrendListModelImpl
import com.example.nuonuo.view.TrendView

class TrendPresenterImpl(private val trendView: TrendView): TrendPresenter.OnGetTrendListDataListener {

    private val trendListModelImpl:TrendListModelImpl = TrendListModelImpl()


    override fun getTrendListData() {
        trendListModelImpl.getTrendList(this)
    }

    override fun getTrendListDataFailed(errorMessage: String?) {
        trendView.getDataFailed(errorMessage)
    }

    override fun getTrendListDataSuccess() {
        trendView.getSuccess()
    }
}