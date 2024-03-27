package com.ssafy.d101.utils

import android.content.SharedPreferences

class SecureSharedPreferences(private val sharedPref: SharedPreferences) {
    fun contains(key: String) = sharedPref.contains(key)

    fun get(key: String, defaultValue: Boolean): Boolean = getInternal(key, defaultValue)
    fun get(key: String ,defaultValue: Int): Int = getInternal(key, defaultValue)
    fun get(key: String, defaultValue: Long): Long = getInternal(key, defaultValue)
    fun get(key: String, defaultValue: String): String = getInternal(key, defaultValue)

    private fun <T: Any> getInternal(key: String, defaultValue: T): T {
        val str = sharedPref.getString(key, "")
        if (str.isNullOrEmpty()) {
            return defaultValue
        }
        val value = AndroidRsaCipherHelper.decrypt(str)

        return when(defaultValue) {
            is Boolean -> value.toBoolean()
            is Int -> value.toInt()
            is Long -> value.toLong()
            is String -> value.toString()
            else -> throw IllegalArgumentException("defaultValue only could be one of Boolean, Int, Long, String")
        } as T
    }

    fun put(key: String, value: Boolean) = putInternal(key, value)
    fun put(key: String, value: Int) = putInternal(key, value)
    fun put(key: String, value: Long) = putInternal(key, value)
    fun put(key: String, value: String) = putInternal(key, value)

    private fun putInternal(key: String, value: Any?) {
        try {
            sharedPref.edit().run {
                if (value == null) {
                    remove(key)
                } else {
                    putString(key, AndroidRsaCipherHelper.encrypt(value.toString()))
                }

                apply()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    companion object {
        fun wrap(sharedPref: SharedPreferences) = SecureSharedPreferences(sharedPref)
    }
}