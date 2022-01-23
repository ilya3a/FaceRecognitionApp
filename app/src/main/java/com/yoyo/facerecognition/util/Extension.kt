package com.yoyo.facerecognition.util

import android.graphics.Bitmap
import android.graphics.Matrix

fun Bitmap.rotate(angle: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(angle) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}