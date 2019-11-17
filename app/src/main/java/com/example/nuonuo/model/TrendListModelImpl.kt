package com.example.nuonuo.model

import com.example.nuonuo.bean.TrendBean
import com.example.nuonuo.bean.TrendLab
import com.example.nuonuo.presenter.TrendPresenter

class TrendListModelImpl: TrendListModel {
    override fun getTrendList(trendListDataListener: TrendPresenter.OnGetTrendListDataListener) {

//        data class TrendBean (
//            var name:String?,
//            var time:String?,
//            var headImgUrl: String?,
//            var content: String?,
//            var contentNum: Int?,
//            var shareNum: Int?,
//            var thumbNum: Int?,
//            var isThumbed: Boolean?,
//            var imgsUrl: List<String>?,
//            var leaves: List<leave>?
//        ){
//            data class leave(var leaveName: String?,var leaveContent: String?)
//        }
        var list = ArrayList<String>()
        list.add("")
        list.add("")
        var list2 = ArrayList<TrendBean.leave>()
        list2.add(TrendBean.leave("sy","LSH 牛逼"))
        list2.add(TrendBean.leave("wy","LSH 牛逼！！"))

        with(TrendBean("Aka1i","五分钟前","","卧槽我车丢了",10,111,12,true,list,list2 )){
            TrendLab.datas.add(this)
        }
        with(TrendBean("Aka1i","五分钟前","","卧槽我车丢了",10,111,12,true,list,list2 )){
            TrendLab.datas.add(this)
        }
        trendListDataListener.getTrendListDataSuccess()
    }
}