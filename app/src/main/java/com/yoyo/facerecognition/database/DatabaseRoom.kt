package com.yoyo.facerecognition.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yoyo.facerecognition.database.dao.PicInfoDao
import com.yoyo.facerecognition.database.misc.Converters
import com.yoyo.facerecognition.database.models.PicInfoDataModel


private const val DATABASE = "Database-Room"

@Database(entities = [PicInfoDataModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun picInfoDao(): PicInfoDao


    companion object {
        @Volatile
        private var iInstance: DatabaseRoom? = null
        fun getDatabase(context: Context): DatabaseRoom {
            val tempInstance = iInstance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, DatabaseRoom::class.java, DATABASE).build()
                iInstance = instance
                return instance
            }
        }
    }
}