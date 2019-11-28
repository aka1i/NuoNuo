package com.example.nuonuo.utils

import android.util.Log
import com.example.nuonuo.bean.BaiduOCRResponse

/**
@author yjn
@create 2019/11/28 - 23:28
 */
class BaiduAPIParese {
    companion object{
        fun paraseORCResponse(baiduOCRResponse: BaiduOCRResponse): String?{
            if (baiduOCRResponse.words_result_num != 0){
                baiduOCRResponse.words_result.forEach {
                    if (Regex("^[0-9a-zA-Z]{5}$").find(it.words) != null)
                        return Regex("^[0-9a-zA-Z]{5}$").find(it.words)?.value
                }
            }
            return null
        }
    }
}