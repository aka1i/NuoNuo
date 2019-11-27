package com.example.nuonuo.bean

/**
@author yjn
@create 2019/11/27 - 17:20
 */
data class BaiduOCRResponse(
    var log_id: Long,
    var words_result_num: Int,
    var words_result: List<Data>
) {
    data class Data(
        var words: String
    )
}