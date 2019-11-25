package com.example.nuonuo.ui.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.example.nuonuo.R
import com.example.nuonuo.adapter.EasyImgAdapter
import com.leochuan.CenterSnapHelper
import com.leochuan.ScaleLayoutManager
import com.leochuan.ViewPagerLayoutManager
import kotlinx.android.synthetic.main.activity_select_owner.*

class SelectOwnerActivity : AppCompatActivity(), View.OnClickListener {
    private val imgUrls = mutableListOf<String>()
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
        val ids = listOf(R.drawable.login_bg,R.drawable.test_bg)
        for (id in ids) {
            val uri = Uri.parse("android.resource://$packageName/$id")
            imgUrls.add(uri.toString())
        }
        val adapter = EasyImgAdapter(this, imgUrls)
        val layoutManager = ScaleLayoutManager.Builder(this, 5)
            .setMaxVisibleItemCount(5)
            .setMoveSpeed(0.5.toFloat())
            .build()
        initIndicate()
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
}
