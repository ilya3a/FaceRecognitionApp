package com.yoyo.facerecognition.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPrefsHelper private constructor(context: Context) {
    companion object : SingletonHolder<SharedPrefsHelper, Context>(::SharedPrefsHelper)

    private val sharedPreferences = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE)

    fun setAppForeground() {
        sharedPreferences.edit().putBoolean(APP_STATE, true).commit()
    }

    fun setAppBackground() {
        sharedPreferences.edit().putBoolean(APP_STATE, false).commit()
    }

    fun isAppOnForeground(): Boolean {
        return sharedPreferences.getBoolean(APP_STATE, false)
    }

    fun setNumberOfFacePics(value: Int) {
        sharedPreferences.edit().putInt(NUM_OF_FACE_PICS, value).commit()
    }

    fun getNumberOfFacePics(): Int {
        return sharedPreferences.getInt(NUM_OF_FACE_PICS, -1)
    }

    fun setNumberOfTotalPics(value: Int) {
        sharedPreferences.edit().putInt(NUM_OF_TOTAL_PICS, value).commit()
    }

    fun getNumberOfTotalPics(): Int {
        return sharedPreferences.getInt(NUM_OF_TOTAL_PICS, -1)
    }
}