package com.example.nuonuo.model

import com.example.nuonuo.bean.UploadFileResponse
import kotlinx.coroutines.Deferred

/**
@author yjn
@create 2019/11/28 - 15:59
 */
interface UploadFileModel {

    fun uploadFile(filePath: String, accessToken: String,type: Int): Deferred<UploadFileResponse>

}