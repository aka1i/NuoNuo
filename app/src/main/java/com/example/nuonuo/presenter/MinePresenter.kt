package com.example.nuonuo.presenter

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.UserInfo

/**
@author yjn
@create 2019/11/26 - 20:40
 */
interface MinePresenter {

    interface OnModifyListener{

        fun modify(userInfo: UserInfo,accessToken: String)

        fun modifySuccess(result: LoginResponse)

        fun modifyFailed(errorMessage: String?)
    }

}