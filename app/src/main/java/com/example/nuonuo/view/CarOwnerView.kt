package com.example.nuonuo.view

/**
@author yjn
@create 2019/11/27 - 1:59
 */
interface CarOwnerView {
    fun senMessage(content:String,accessToken:String,uid: Int,phone: String)

    fun senMessageeFailed(errorMessage: String?)

    fun  senMessageSuccess()
}