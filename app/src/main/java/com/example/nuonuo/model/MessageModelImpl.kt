package com.example.nuonuo.model

import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.api.BasicCallback
import com.example.nuonuo.bean.*
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.MessagePresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class MessageModelImpl: MessageModel {
    private  var sendMessageListResponseAsyn: Deferred<MessageListResponse>? = null
    private  var receiveMessageListResponseAsyn: Deferred<MessageListResponse>? = null
    private var SendMessageResponseAsyn: Deferred<SendMessageResponse>? = null
    private var getPhonePicResponseAsyn: Deferred<PhoneCodeResponse>? = null
    private var phoneCallResponseAsyn: Deferred<GetPhoneCallResponse>? = null
    private var dianzanResponseAsyn: Deferred<LoginResponse>? = null
    override fun getSend(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                sendMessageListResponseAsyn = RetrofitHelper.retrofitService.getMessageSendList(accessToken)
                val result = sendMessageListResponseAsyn?.await()
                if (result == null){
                    onMessagePresenterListener.getSendFailed(Constant.RESULT_NULL)
                }else{
                    MessageLab.sendMessages.clear()
                    result.data?.run {
                        forEach {
                            MessageLab.sendMessages.add(it)
                        }
                    }
                    onMessagePresenterListener.getSentSuccess()
                }


            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onMessagePresenterListener.getSendFailed(e.response().errorBody()?.string())
                }else{
                    onMessagePresenterListener.getSendFailed(e.message)
                }
            }
        }
    }

    override fun getReceive(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                receiveMessageListResponseAsyn = RetrofitHelper.retrofitService.getMessageReciveList(accessToken)
                val result = receiveMessageListResponseAsyn?.await()
                if (result == null){
                    onMessagePresenterListener.getReceiveFailed(Constant.RESULT_NULL)
                }else
                {
                    MessageLab.receiveMessage.clear()
                    result.data?.run {
                        forEach {
                            MessageLab.receiveMessage.add(it)
                        }
                    }
                    onMessagePresenterListener.getReceiveSuccess()
                }


            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onMessagePresenterListener.getReceiveFailed(e.response().errorBody()?.string())
                }else{
                    onMessagePresenterListener.getReceiveFailed(e.message)
                }
            }
        }
    }

    override fun sendMessage(onSendMessageListener: MessagePresenter.OnSendMessageListener,content: String, accessToken: String, uid: Int,phone: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val jsonObject = JSONObject()
                jsonObject.put("content",content)
                SendMessageResponseAsyn = RetrofitHelper.retrofitService.sendMessage(
                    RetrofitHelper.getRequestBodyByJson(jsonObject),
                    accessToken,
                    uid)
                val result = SendMessageResponseAsyn?.await()
                if (result == null){
                    onSendMessageListener.senMessageeFailed(Constant.RESULT_NULL)
                }else
                {
                    val message = JMessageClient.createSingleTextMessage(phone, Constant.IM_APP_KEY,content)

                    message?.setOnSendCompleteCallback(object : BasicCallback(){
                        override fun gotResult(p0: Int, p1: String?) {
                        }
                    })
                    JMessageClient.sendMessage(message)
                    onSendMessageListener.senMessageSuccess()

                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onSendMessageListener.senMessageeFailed(e.response().errorBody()?.string())
                }else{
                    onSendMessageListener.senMessageeFailed(e.message)
                }
            }
        }
    }

    override fun getPhoneCookieAndToken(onGetPhoneCookieAndTokenListener: MessagePresenter.OnGetPhoneCookieAndTokenListener) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                getPhonePicResponseAsyn = RetrofitHelper.retrofitService.getPhoneCode()
                val result = getPhonePicResponseAsyn?.await()
                if (result == null){
                    onGetPhoneCookieAndTokenListener.getPhoneCookieAndTokenFailed(Constant.RESULT_NULL)
                }else
                {
                    onGetPhoneCookieAndTokenListener.getPhoneCookieAndTokenSuccess(result)
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onGetPhoneCookieAndTokenListener.getPhoneCookieAndTokenFailed(e.response().errorBody()?.string())
                }else{
                    onGetPhoneCookieAndTokenListener.getPhoneCookieAndTokenFailed(e.message)
                }
            }
        }
    }

    override fun phoneCall(
        onPhoneCallListener: MessagePresenter.OnPhoneCallListener,
        phoneCallBean: PhoneCallBean,
        accessToken: String
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                phoneCallResponseAsyn = RetrofitHelper.retrofitService.phoneCall(phoneCallBean,accessToken)
                val result = phoneCallResponseAsyn?.await()
                if (result == null){
                    onPhoneCallListener.phoneCallFailed(Constant.RESULT_NULL)
                }else
                {
                    onPhoneCallListener.phoneCallSuccess(result)
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onPhoneCallListener.phoneCallFailed(e.response().errorBody()?.string())
                }else{
                    onPhoneCallListener.phoneCallFailed(e.message)
                }
            }
        }
    }

    override fun dianzan(
        onDianzanListener: MessagePresenter.OnDianzanListener,
        id: Int,
        accessToken: String
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                dianzanResponseAsyn = RetrofitHelper.retrofitService.dianzan(id,accessToken)
                val result = dianzanResponseAsyn?.await()
                if (result == null){
                    onDianzanListener.dianzanFailed(Constant.RESULT_NULL)
                }else
                {
                    onDianzanListener.dianzanSuccess(result)
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is HttpException){
                    onDianzanListener.dianzanFailed(e.response().errorBody()?.string())
                }else{
                    onDianzanListener.dianzanFailed(e.message)
                }
            }
        }
    }
}