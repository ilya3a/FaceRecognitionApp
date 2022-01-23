package com.yoyo.facerecognition.repo

import com.yoyo.facerecognition.database.models.PicInfoDataModel
import kotlinx.coroutines.flow.Flow

interface IPicInfoRepo {

    fun getAllPicInfoFlow(): Flow<List<PicInfoDataModel>>
    fun getFacePicInfoFlow():Flow<List<PicInfoDataModel>>
    fun getNonFacePicInfoFlow():Flow<List<PicInfoDataModel>>
    fun getAllPicInfo():List<PicInfoDataModel>
    fun addPicturesToDB(listOfPics: List<PicInfoDataModel>)


}