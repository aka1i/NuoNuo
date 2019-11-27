package com.example.nuonuo.presenter

import com.example.nuonuo.bean.NewTrend
import com.example.nuonuo.bean.NewTrendResponse
import com.example.nuonuo.view.NewTrendView

/**
@author yjn
@create 2019/11/27 - 23:16
 */
interface NewTrendPresenter{

    interface OnNewTrendListerner{

        fun newTrend(newTrend: NewTrend,accessToken:String)

        fun newTrendFailed(errorMessage: String?)

        fun newTrendSuccess(newTrendResponse: NewTrendResponse)
    }

}