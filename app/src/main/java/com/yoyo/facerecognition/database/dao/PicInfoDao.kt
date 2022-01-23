package com.yoyo.facerecognition.database.dao

import androidx.room.*
import com.yoyo.facerecognition.database.misc.Converters
import com.yoyo.facerecognition.database.misc.PicSection
import com.yoyo.facerecognition.database.models.PicInfoDataModel
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(Converters::class)
interface PicInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPicInfoList(picInfoList: List<PicInfoDataModel>)

    @Query("DELETE FROM pic_info")
    fun deleteAllGeoInfo()

    @Query("SELECT * FROM pic_info ORDER BY id")
    fun getPicInfoListFlow(): Flow<List<PicInfoDataModel>>

    @Query("SELECT * FROM pic_info ORDER BY id")
    fun getPicInfoList(): List<PicInfoDataModel>

    @Query("SELECT * FROM pic_info WHERE section = :section ORDER BY id")
    fun getPicInfoListBySection(section: PicSection): Flow<List<PicInfoDataModel>>

}
