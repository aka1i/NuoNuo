package com.example.mykotlin.base

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.nuonuo.marco.Constant
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(private val name: String,private val default: T) : ReadWriteProperty<Any?,T> {
    companion object {
        lateinit var preferences: SharedPreferences

        /**
         * init Context
         * @param context Context
         */
        fun setContext(context: Context) {
            preferences = context.getSharedPreferences(
                context.packageName + Constant.SHARED_NAME,
                Context.MODE_PRIVATE
            )
        }

        fun clear() {
            preferences.edit().clear().apply()
        }
    }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(name,default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putPreference(name,value)

    private fun <T> findPreference(name: String,default: T): T = with(preferences){
        val res = when(default){
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        res as T
    }


    private fun <U> putPreference(name: String, value: U) = with(preferences.edit()) {
        Log.d("123123",value as String)
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }
}