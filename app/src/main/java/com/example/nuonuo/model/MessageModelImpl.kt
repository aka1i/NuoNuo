package com.example.nuonuo.model

import android.util.Log
import android.widget.Toast
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.api.BasicCallback
import com.example.mykotlin.base.Preference
import com.example.nuonuo.bean.MessageLab
import com.example.nuonuo.bean.MessageListResponse
import com.example.nuonuo.bean.SendMessageResponse
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
                    Log.d("jiguang_123",phone)
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
}