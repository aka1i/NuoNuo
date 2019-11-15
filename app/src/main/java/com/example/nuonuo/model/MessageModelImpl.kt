package com.example.nuonuo.model

import com.example.nuonuo.bean.MessageItemBean
import com.example.nuonuo.bean.MessageLab
import com.example.nuonuo.presenter.MessagePresenter

class MessageModelImpl: MessageModel {
    override fun getSend(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener) {
        with(MessageItemBean("sy","卧槽我车丢了","2019/11/11","")){
            MessageLab.sendMessages.add(this)
        }
        with(MessageItemBean("sy","卧槽我车丢了","2019/11/11","")){
            MessageLab.sendMessages.add(this)
        }
        with(MessageItemBean("sy","卧槽我车丢了","2019/11/11","")){
            MessageLab.sendMessages.add(this)
        }
        with(MessageItemBean("sy","卧槽我车丢了","2019/11/11","")){
            MessageLab.sendMessages.add(this)
        }
        with(MessageItemBean("sy","卧槽我车丢了","2019/11/11","")){
            MessageLab.sendMessages.add(this)
        }
        with(MessageItemBean("sy","卧槽我车丢了","2019/11/11","")){
            MessageLab.sendMessages.add(this)
        }
        onMessagePresenterListener.getSentSuccess()
    }

    override fun getReceive(onMessagePresenterListener: MessagePresenter.OnMessagePresenterListener) {
        with(MessageItemBean("wy","卧槽我车丢了","2019/11/11","")){
            MessageLab.receiveMessage.add(this)
        }
        with(MessageItemBean("wy","卧槽我车丢了","2019/11/11","")){
            MessageLab.receiveMessage.add(this)
        }
        with(MessageItemBean("wy","卧槽我车丢了","2019/11/11","")){
            MessageLab.receiveMessage.add(this)
        }
        with(MessageItemBean("wy","卧槽我车丢了","2019/11/11","")){
            MessageLab.receiveMessage.add(this)
        }
        with(MessageItemBean("wy","卧槽我车丢了","2019/11/11","")){
            MessageLab.receiveMessage.add(this)
        }
        onMessagePresenterListener.getReceiveSuccess()
    }
}