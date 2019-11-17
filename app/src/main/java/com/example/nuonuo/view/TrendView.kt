package com.example.nuonuo.view

interface TrendView {

    fun getDataFailed(errorMessage: String?)

    fun getSuccess()

    fun refresh()

}