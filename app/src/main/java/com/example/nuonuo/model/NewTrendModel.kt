package com.example.nuonuo.model

import com.example.nuonuo.bean.NewTrend
import com.example.nuonuo.presenter.NewTrendPresenter

/**
@author yjn
@create 2019/11/27 - 23:26
 */
interface NewTrendModel {

    fun newTrend(onNewTrendListerner: NewTrendPresenter.OnNewTrendListerner,newTrend: NewTrend,accessToken: String)

}