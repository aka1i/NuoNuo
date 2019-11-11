package com.example.nuonuo.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent
import kotlin.math.abs
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




/**
@author yjn
@create 2019/11/10 - 16:13
 */
/**
 * Created by Suzi on 2016/11/14.
 * 自定义ViewPage，可以设置ViewPager页面之间不能滑动
 */

class MyViewPager : ViewPager {

    /*
    更改scrollble的值可设置是否可滑动，默认true为可滑动
     */
    var isScrollble = true

    private var startX: Int = 0
    private var startY: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (!isScrollble) {
            true
        } else super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {

                //                int dX = (int) (ev.getX() - startX);
                val dY = (ev.y - startX).toInt()
                return abs(dY) <= 0
            }
            MotionEvent.ACTION_UP -> {
            }
        }

        return super.onInterceptTouchEvent(ev)
    }


}
