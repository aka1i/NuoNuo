package com.example.nuonuo.bean

import java.io.Serializable

data class RegisterResponse(
    var status: Int,
    var message: String?
): Serializable