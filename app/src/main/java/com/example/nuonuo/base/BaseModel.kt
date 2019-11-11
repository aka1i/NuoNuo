package com.example.nuonuo.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
@author yjn
@create 2019/11/11 - 12:13
 */
abstract class BaseModel: CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        Job() + Dispatchers.Main
    }
}