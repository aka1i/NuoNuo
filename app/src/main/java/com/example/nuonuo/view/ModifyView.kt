package com.example.nuonuo.view

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.UserInfo

/**
@author yjn
@create 2019/11/26 - 20:52
 */
interface ModifyView {

    fun modify()

    fun modifySuccess(result: LoginResponse)

    fun modifyFailed(errorMessage: String?)

}