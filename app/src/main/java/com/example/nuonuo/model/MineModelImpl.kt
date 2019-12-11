package com.example.nuonuo.model

import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.GetUserInfoCallback
import cn.jpush.im.api.BasicCallback
import com.example.mykotlin.base.Preference
import com.example.nuonuo.bean.FileBean
import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.UploadFileResponse
import com.example.nuonuo.bean.UserInfo
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.MinePresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

/**
@author yjn
@create 2019/11/26 - 20:50
 */
class MineModelImpl: MineModel {


    private val uploadFileModelImpl: UploadFileModelImpl = UploadFileModelImpl()

    private var headResponse: UploadFileResponse? = null

    private  var modifyResponseAsyn: Deferred<LoginResponse>? = null

    private var phone: String by Preference(Constant.PHONE_KEY,"")

    override fun modifyUserInfo(modifyListener: MinePresenter.OnModifyListener,userInfo: UserInfo,accessToken: String,headFile: FileBean) {
        GlobalScope.launch(Dispatchers.Main) {
            try {

                headFile.newFilePath?.run {
                    headResponse =
                        uploadFileModelImpl.uploadFile(this,accessToken,UploadFileModelImpl.UPLOAD_IMAGE).await()
                            .run {
                                if (this.status != 0)
                                    throw Exception(this.message)

                                userInfo.headPicId = this.data?.id
                                this
                            }
                }

                modifyResponseAsyn = RetrofitHelper.retrofitService.modifyUserInfo(userInfo,accessToken)
                val result = modifyResponseAsyn?.await()
                if (result == null){
                    modifyListener.modifyFailed(Constant.RESULT_NULL)
                }else{
                    headFile.res = headResponse?.data?.url
                    headFile.newId = headResponse?.data?.id
                    JMessageClient.getUserInfo(phone, Constant.PHONE_KEY, object : GetUserInfoCallback(){
                        override fun gotResult(
                            p0: Int,
                            p1: String?,
                            p2: cn.jpush.im.android.api.model.UserInfo?
                        ) {
                            p2?.nickname = userInfo.name
                            JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname
                                ,p2,object : BasicCallback(){
                                override fun gotResult(p0: Int, p1: String?) {

                                }
                            })
                        }
                    })


                    modifyListener.modifySuccess(result)
                }

            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    modifyListener.modifyFailed(e.response().errorBody()?.string())
                }else{
                    modifyListener.modifyFailed(e.message)
                }
            }
        }
    }
}