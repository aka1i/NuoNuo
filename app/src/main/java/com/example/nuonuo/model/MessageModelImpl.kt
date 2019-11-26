package com.example.nuonuo.model

import android.util.Log
import com.example.nuonuo.bean.MessageLab
import com.example.nuonuo.bean.MessageListResponse
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.MessagePresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.math.log

class MessageModelImpl: MessageModel {
    private  var sendMessageListResponseAsyn: Deferred<MessageListResponse>? = null
    private  var receiveMessageListResponseAsyn: Deferred<MessageListResponse>? = null
    override fun getSend(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener,accessToken:String,uid:Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                sendMessageListResponseAsyn = RetrofitHelper.retrofitService.getMessageList(accessToken)
                val result = sendMessageListResponseAsyn?.await()
                if (result == null){
                    onMessagePresenterListener.getSendFailed(Constant.RESULT_NULL)
                }else{
                    MessageLab.sendMessages.clear()
                    result.data?.run {
                        forEach {
                            if (it.sendId==uid)
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
                receiveMessageListResponseAsyn = RetrofitHelper.retrofitService.getMessageList(accessToken)
                val result = receiveMessageListResponseAsyn?.await()
                if (result == null){
                    onMessagePresenterListener.getReceiveFailed(Constant.RESULT_NULL)
                }else
                {
                    MessageLab.receiveMessage.clear()
                    result.data?.run {
                        forEach {
                            if (it.getId==uid)
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
}