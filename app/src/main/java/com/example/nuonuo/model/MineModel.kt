package com.example.nuonuo.model

import com.example.nuonuo.bean.UserInfo
import com.example.nuonuo.presenter.MinePresenter

/**
@author yjn
@create 2019/11/26 - 20:49
 */
interface MineModel {
    fun modifyUserInfo(modifyListener: MinePresenter.OnModifyListener,userInfo: UserInfo,accessToken: String)
}