package com.yoyo.facerecognition.repo

import android.content.Context
import android.os.Environment
import com.yoyo.facerecognition.database.DatabaseRoom
import com.yoyo.facerecognition.database.misc.PicSection
import com.yoyo.facerecognition.database.models.PicInfoDataModel
import com.yoyo.facerecognition.util.SingletonHolder
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.util.*
import javax.inject.Inject

class PicInfoRepo
@Inject
constructor(private val context: Context) : IPicInfoRepo {
    companion object{
        private const val APP_PREFS = "app_prefs"
    }


    private val sharedPref = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    private val picInfoDao = DatabaseRoom.getDatabase(context).picInfoDao()

    override fun getAllPicInfoFlow(): Flow<List<PicInfoDataModel>> {
        val data = picInfoDao.getPicInfoList()

        if (data.isEmpty()) {
            //need to get data from device and fill the DB
            val folderPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + "/oosto/"
            browseFolder(folderPath)
        }

        return picInfoDao.getPicInfoListFlow()

    }

    override fun getFacePicInfoFlow(): Flow<List<PicInfoDataModel>> {
        return picInfoDao.getPicInfoListBySection(PicSection.FACES)
    }

    override fun getNonFacePicInfoFlow(): Flow<List<PicInfoDataModel>> {
        return picInfoDao.getPicInfoListBySection(PicSection.NON_FACES)
    }

    override fun getAllPicInfo(): List<PicInfoDataModel> {
        return picInfoDao.getPicInfoList()
    }

    override fun addPicturesToDB(listOfPics: List<PicInfoDataModel>) {
        picInfoDao.addPicInfoList(listOfPics)
    }

    private fun browseFolder(folder: String) {
        val file = File(folder)
        //to end the recursive loop
        if (!file.exists()) return
        //if directory, go inside and call recursively
        if (file.isDirectory) {
            for (f in Objects.requireNonNull(file.listFiles())) {
                //call recursively
                browseFolder(f.path)
            }
        }
        //add file to db
        if (file.path.endsWith(".jpg")) {
            picInfoDao.addPicInfoList(listOf(PicInfoDataModel(file.path)))
        }
        return
    }
}