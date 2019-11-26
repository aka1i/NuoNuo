package com.example.nuonuo.utils

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_mine_setting.*

/**
@author yjn
@create 2019/11/25 - 19:09
 */
class PopUpUtil {
    companion object{
        fun closePopupWindow(pop: PopupWindow) {
            if (pop.isShowing) {
                pop.dismiss()
            }
        }

        fun showProgressBar(window: Window,progressBar: ProgressBar){
            val lp = window.attributes
            lp.alpha = 0.5f
            window.attributes = lp
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,  WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.visibility = View.VISIBLE;
        }
        fun cancelProgressBar(window: Window,progressBar: ProgressBar){
            progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            val lp = window.attributes
            lp.alpha = 1f
            window.attributes = lp
        }
    }

}