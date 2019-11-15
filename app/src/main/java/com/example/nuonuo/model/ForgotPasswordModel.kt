package com.example.nuonuo.model

interface ForgotPasswordModel {

    fun getEamilCode(email:String)

    fun forgotPassword(email: String, password: String, code: String)

}