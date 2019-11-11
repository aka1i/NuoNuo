package com.example.nuonuo.presenter

import com.example.nuonuo.bean.RegisterResponse

/**
@author yjn
@create 2019/11/10 - 19:13
 */
interface RegisterPresenter {

    interface OnRegisterListener {
        /**
         * register
         * @param username username
         * @param password password
         * @param email email
         */
        fun register(username: String, password: String, email: String)

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

}