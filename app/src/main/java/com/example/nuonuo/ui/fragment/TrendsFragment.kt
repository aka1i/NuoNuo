package com.example.nuonuo.ui.fragment


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.mykotlin.base.Preference

import com.example.nuonuo.R
import com.example.nuonuo.adapter.TrendAdapter
import com.example.nuonuo.bean.TrendLab
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.presenter.TrendPresenterImpl
import com.example.nuonuo.ui.activity.NewTrendActivity
import com.example.nuonuo.utils.HeadImgUtil
import com.example.nuonuo.view.TrendView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_treands.*
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class TrendsFragment : Fragment(), TrendView, View.OnClickListener{

    private val name: String by Preference(Constant.USER_NAME_KEY,"")

    private lateinit var myView: View

    private lateinit var trendList: RecyclerView

    private lateinit var refreshLayout: SwipeRefreshLayout

    private lateinit var nameText: TextView

    private lateinit var headImg: ImageView

    private var trendAdapter: TrendAdapter? = null

    private lateinit var floatingActionButton: FloatingActionButton

    private var sexual: String by Preference(Constant.SEXUAL_KEY,"")

    private var headPicUrl: String? by Preference(Constant.HEAD_PIC_URL_KEY,"")


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
            headImg = myView.findViewById(R.id.head_img)
            refreshLayout = myView.findViewById(R.id.swipeRefreshLayout)
            trendList.layoutManager = LinearLayoutManager(activity)
            nameText = myView.findViewById(R.id.name_text)
            floatingActionButton = myView.findViewById(R.id.floating_btn)

            val options = HeadImgUtil.getHeadImgOptions(sexual)
            Glide.with(this).load(headPicUrl).apply(options).into(headImg)
            floatingActionButton.setOnClickListener(this@TrendsFragment)

            nameText.text = name
            trendAdapter = TrendAdapter(TrendLab.datas,this)
            trendList.adapter = trendAdapter
            trendPresenterImpl.getTrendListData()
            refreshLayout.setOnRefreshListener {
                trendPresenterImpl.getTrendListData()
            }
        }


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.floating_btn ->{
                startActivityForResult(Intent(activity, NewTrendActivity::class.java),Constant.START_NEW_TREND)
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
        trendAdapter?.beans = TrendLab.datas
        trendAdapter?.notifyDataSetChanged()
    }

    override fun stopRefresh() {
        if(refreshLayout.isRefreshing)
            refreshLayout.isRefreshing = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            Constant.START_NEW_TREND -> {
                when(resultCode){
                    RESULT_OK ->{
                        trendPresenterImpl.getTrendListData()
                    }
                }
            }
        }
    }

    fun refreshSelfData(){
        val options = HeadImgUtil.getHeadImgOptions(sexual)
        Glide.with(this).load(headPicUrl).apply(options).into(headImg)
    }
}
