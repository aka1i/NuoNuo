package com.example.nuonuo.utils

import android.widget.PopupWindow

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
    }

}