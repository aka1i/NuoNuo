package com.example.nuonuo.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.nuonuo.R
import com.example.nuonuo.adapter.EasyImgAdapter
import com.example.nuonuo.base.BaseActivity
import com.example.nuonuo.bean.GetCarOnwerResponse
import com.example.nuonuo.presenter.SelectCarOwnerPresenterImpl
import com.example.nuonuo.utils.PopUpUtil
import com.example.nuonuo.view.SelectCarOwnerView
import com.leochuan.CenterSnapHelper
import com.leochuan.ScaleLayoutManager
import com.leochuan.ViewPagerLayoutManager
import kotlinx.android.synthetic.main.activity_select_owner.*

class SelectOwnerActivity :BaseActivity(), View.OnClickListener, SelectCarOwnerView {

    companion object{
        fun newIntent(context: Context,id: String):Intent{
            val intent = Intent(context,SelectOwnerActivity::class.java)
            intent.putExtra("photoId",id)
            return intent
        }
    }

    private var id: String = ""

    private val selectCarOwnerPresenterImpl: SelectCarOwnerPresenterImpl by lazy {
        SelectCarOwnerPresenterImpl(this)
    }

    private val imgUrls = mutableListOf<String>()

    private lateinit var adapter: EasyImgAdapter

    private lateinit var datas: List<GetCarOnwerResponse.Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_owner)
        init()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.car_photo_back ->{
                finish()
            }
        }
    }

    private fun init(){
        id = intent.getStringExtra("photoId")

//        val ids = listOf(R.drawable.login_bg,R.drawable.test_bg)
//        for (id in ids) {
//            val uri = Uri.parse("android.resource://$packageName/$id")
//            imgUrls.add(uri.toString())
//        }
        adapter = EasyImgAdapter(this, imgUrls)
        adapter.setOnItemClickedListener { position -> startActivity(CarOwnerActivity.newIntent(this@SelectOwnerActivity,datas[position].uid,datas[position].name,datas[position].headPicUrl ?: "")) }
        val layoutManager = ScaleLayoutManager.Builder(this, 5)
            .setMaxVisibleItemCount(5)
            .setMoveSpeed(0.5.toFloat())
            .build()
        layoutManager.setOnPageChangeListener(object : ViewPagerLayoutManager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageSelected(p0: Int) {
                for (i in 0 until imgUrls.size){
                    indicator.getChildAt(i).setBackgroundResource(if (i == p0) R.drawable.dot_focus else R.drawable.dot_normal)
                }
            }
        })
        owner_list.adapter = adapter
        owner_list.layoutManager = layoutManager
        CenterSnapHelper().attachToRecyclerView(owner_list)

        car_photo_back.setOnClickListener(this)
        getCarOwnerList(id)

        PopUpUtil.showProgressBar(window,progressBar)
    }

    private fun initIndicate(){
        val width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,resources.displayMetrics).toInt()
        var lp = LinearLayout.LayoutParams(width, width)
        lp.setMargins(width,0, width,0)
        for (i in 0 until  imgUrls.size){
            val view = View(this)
            view.id = i
            view.setBackgroundResource(if (i == 0) R.drawable.dot_focus else R.drawable.dot_normal)
            view.layoutParams = lp
            indicator.addView(view,i)
        }
    }

    override fun getCarOwnerList(id: String) {
        selectCarOwnerPresenterImpl.getCarOwnerList(id)
    }

    override fun getCarOwnerListSuccess(getCarOnwerResponse: GetCarOnwerResponse) {
        imgUrls.clear()
        getCarOnwerResponse.data?.run {
            datas = this
            this
        }?.forEach {
            imgUrls.add(it.otherPicNames[0])
        }
        initIndicate()
        adapter.notifyDataSetChanged()
        PopUpUtil.cancelProgressBar(window,progressBar)
    }

    override fun getCarOwnerListFailed(errorString: String?) {
        Toast.makeText(this,errorString,Toast.LENGTH_SHORT).show()
        finish()
        PopUpUtil.cancelProgressBar(window,progressBar)
    }
}
