package com.example.nuonuo.presenter

import com.example.nuonuo.bean.NewTrend
import com.example.nuonuo.bean.NewTrendResponse
import com.example.nuonuo.model.NewTrendModelImpl
import com.example.nuonuo.presenter.NewTrendPresenter
import com.example.nuonuo.view.NewTrendView

/**
@author yjn
@create 2019/11/27 - 23:19
 */
class NewTrendPresenterImpl(private val newTrendView: NewTrendView): NewTrendPresenter.OnNewTrendListerner {

    private val newTrendModelImpl: NewTrendModelImpl = NewTrendModelImpl()

    override fun newTrend(newTrend: NewTrend,accessToken:String) {
        newTrendModelImpl.newTrend(this,newTrend,accessToken)
    }

    override fun newTrendFailed(errorMessage: String?) {
        newTrendView.newTrendFailed(errorMessage)
    }

    override fun newTrendSuccess(newTrendResponse: NewTrendResponse) {
        newTrendView.newTrendSuccess()
    }
}