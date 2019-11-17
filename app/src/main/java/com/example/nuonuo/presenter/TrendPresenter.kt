package com.example.nuonuo.presenter

interface TrendPresenter {
    interface OnGetTrendListDataListener{
        fun getTrendListData()

        fun getTrendListDataFailed(errorMessage: String?)

        fun getTrendListDataSuccess()
    }
}