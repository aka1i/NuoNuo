package com.example.nuonuo.model

import com.example.nuonuo.presenter.TrendPresenter

interface TrendListModel {

    fun getTrendList(trendListDataListener: TrendPresenter.OnGetTrendListDataListener)

}