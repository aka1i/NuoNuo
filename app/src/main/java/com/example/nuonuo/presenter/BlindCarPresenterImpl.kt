package com.example.nuonuo.presenter

import android.util.Log
import com.example.nuonuo.base.BasePresenter
import com.example.nuonuo.bean.*
import com.example.nuonuo.model.BaiduAPIModelImpl
import com.example.nuonuo.model.BlindCarModelImpl
import com.example.nuonuo.view.BlindCarView
import org.json.JSONObject

/**
@author yjn
@create 2019/11/28 - 23:06
 */
class BlindCarPresenterImpl(private val blindCarView: BlindCarView):BasePresenter(blindCarView),BlindCarPresenter.OnBlindCarListener,BlindCarPresenter.OnGetSelfCarCode {

    val blindCarModelImpl = BlindCarModelImpl()

    override fun blindCar(code: String, fileBean: FileBean,accessToken:String) {
        blindCarModelImpl.blindCar(this,code,fileBean,accessToken)
    }

    override fun blindSuccess(loginResponse: LoginResponse) {
        blindCarView.blindSuccess(loginResponse)
    }

    override fun blindFailed(errorMessage: String?) {
        blindCarView.blindFailed(errorMessage)
    }

    override fun getSelfCarCode(accessToken: String) {
        blindCarModelImpl.getSelfCarCode(this,accessToken)
    }

    override fun getSelfCarCodeSuccess(selfCarCodeResponse: SelfCarCodeResponse) {
        blindCarView.getSelfCarCodeSuccess(selfCarCodeResponse)
    }

    override fun getSelfCarCodeFailed(errorMessage: String?) {
        dealCallBackDataByData(errorMessage)
        blindCarView.getSelfCarCodeFailed(errorMessage)
    }
}