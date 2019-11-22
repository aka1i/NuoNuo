package com.example.nuonuo.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mykotlin.base.Preference

import com.example.nuonuo.R
import com.example.nuonuo.adapter.TrendAdapter
import com.example.nuonuo.bean.TrendLab
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.TrendPresenterImpl
import com.example.nuonuo.view.TrendView
import kotlinx.android.synthetic.main.fragment_treands.*
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class TrendsFragment : Fragment(), TrendView{

    private val name: String by Preference(Constant.USER_NAME_KEY,"")

    private lateinit var myView: View

    private lateinit var trendList: RecyclerView

    private lateinit var refreshLayout: SwipeRefreshLayout

    private lateinit var nameText: TextView

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
            refreshLayout = myView.findViewById(R.id.swipeRefreshLayout)
            trendList.layoutManager = LinearLayoutManager(activity)
            nameText = myView.findViewById(R.id.name_text)
            nameText.text = name
            trendAdapter = TrendAdapter(TrendLab.datas,this)
            trendList.adapter = trendAdapter
            trendPresenterImpl.getTrendListData()
            refreshLayout.setOnRefreshListener {
                trendPresenterImpl.getTrendListData()
            }
        }


    }

    override fun getDataFailed(errorMessage: String?) {
        stopRefresh()
        Toast.makeText(activity,errorMessage,Toast.LENGTH_SHORT).show()
    }

    override fun getSuccess() {
        stopRefresh()
        refresh()
    }

    override fun refresh() {
        trendAdapter?.notifyDataSetChanged()
    }

    override fun stopRefresh() {
        if(refreshLayout.isRefreshing)
            refreshLayout.isRefreshing = false
    }
}
