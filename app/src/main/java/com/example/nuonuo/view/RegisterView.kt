package com.example.nuonuo.view

import com.example.nuonuo.bean.RegisterResponse

/**
@author yjn
@create 2019/11/10 - 19:21
 */
interface RegisterView {

    /**
     * register success
     * @param result LoginResponse
     */
    fun registerSuccess(result: RegisterResponse)

    /**
     * register failed
     * @param errorMessage error message
     */
    fun registerFailed(errorMessage: String?)

}