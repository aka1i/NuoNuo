package com.example.nuonuo.view

/**
@author yjn
@create 2019/11/27 - 23:03
 */
interface NewTrendView {

    fun newTrend()

    fun newTrendFailed(errorMessage: String?)

    fun newTrendSuccess()
}