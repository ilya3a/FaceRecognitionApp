package com.yoyo.facerecognition.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yoyo.facerecognition.database.misc.PicSection

@Entity(tableName = "pic_info")
data class PicInfoDataModel(
    val path: String,
    var section: PicSection = PicSection.ALL
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
