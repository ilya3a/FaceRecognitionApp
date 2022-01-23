package com.yoyo.facerecognition.util

import android.content.Context
import com.yoyo.facerecognition.R



const val APP_PREFS = "appPrefs"
const val APP_STATE = "appState"
const val NUM_OF_FACE_PICS = "numOfFacePics"
const val NUM_OF_TOTAL_PICS = "numOfTotalPics"
const val PROGRESS = "progress"


fun getSummeryText(context: Context, numOfFacePics: Int, total: Int): String {
    return "${context.getString(R.string.summery_detect)} $numOfFacePics ${context.getString(R.string.summery_out_of)} $total ${
        context.getString(
            R.string.summery_pictures
        )
    }"
}