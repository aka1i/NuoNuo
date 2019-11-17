package com.example.nuonuo.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.nuonuo.R
import com.example.nuonuo.adapter.TrendAdapter
import com.example.nuonuo.bean.TrendLab
import com.example.nuonuo.presenter.MessagePresenterImpl
import com.example.nuonuo.presenter.TrendPresenterImpl
import com.example.nuonuo.view.MessageView
import com.example.nuonuo.view.TrendView

/**
 * A simple [Fragment] subclass.
 */
class TrendsFragment : Fragment(), TrendView{

    lateinit var myView: View

    lateinit var trendList: RecyclerView

    private var trendAdapter: TrendAdapter? = null

    private val trendPresenterImpl:TrendPresenterImpl by lazy {
        TrendPresenterImpl(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_treands, container, false)
        init()
        return myView
    }


    fun init(){
        activity?.apply {
            trendList = myView.findViewById(R.id.trend_list)
            trendList.layoutManager = LinearLayoutManager(activity)
            trendAdapter = TrendAdapter(TrendLab.datas,this)
            trendList.adapter = trendAdapter
            trendPresenterImpl.getTrendListData()
        }


    }

    override fun getDataFailed(errorMessage: String?) {
        Toast.makeText(activity,errorMessage,Toast.LENGTH_SHORT).show()
    }

    override fun getSuccess() {
        refresh()
    }

    override fun refresh() {
        trendAdapter?.notifyDataSetChanged()
    }
}
