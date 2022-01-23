package com.yoyo.facerecognition.database.misc

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    @TypeConverter
    fun stringToPicSection(value: String): PicSection {
        val listType = object : TypeToken<PicSection>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun picSectionToString(picSection: PicSection): String {
        return Gson().toJson(picSection)
    }
}