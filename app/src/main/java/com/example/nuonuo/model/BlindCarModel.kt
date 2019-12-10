package com.example.nuonuo.model

import com.example.nuonuo.bean.FileBean
import com.example.nuonuo.presenter.BlindCarPresenter

/**
@author yjn
@create 2019/12/10 - 11:37
 */
interface BlindCarModel {

    fun blindCar(onBlindCarListener: BlindCarPresenter.OnBlindCarListener,code: String,fileBean: FileBean,accessToken:String)

    fun getSelfCarCode(onGetSelfCarCode: BlindCarPresenter.OnGetSelfCarCode,accessToken: String)

}