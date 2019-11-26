package com.example.nuonuo.presenter

import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.UserInfo
import com.example.nuonuo.model.MineModelImpl
import com.example.nuonuo.view.ModifyView

/**
@author yjn
@create 2019/11/26 - 20:42
 */
class ModifyPresenterImpl(private val modifyView: ModifyView): MinePresenter.OnModifyListener {

    private val mineModelImpl: MineModelImpl = MineModelImpl()

    override fun modify(userInfo: UserInfo,accessToken: String) {
        mineModelImpl.modifyUserInfo(this,userInfo,accessToken)
    }

    override fun modifySuccess(result: LoginResponse) {
        modifyView.modifySuccess(result)
    }

    override fun modifyFailed(errorMessage: String?) {
        modifyView.modifyFailed(errorMessage)
    }
}