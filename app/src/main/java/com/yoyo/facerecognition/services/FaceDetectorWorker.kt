package com.yoyo.facerecognition.services

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import androidx.core.util.size
import androidx.work.*
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.FaceDetector
import com.yoyo.facerecognition.database.DatabaseRoom
import com.yoyo.facerecognition.database.misc.PicSection
import com.yoyo.facerecognition.repo.IPicInfoRepo
import com.yoyo.facerecognition.repo.PicInfoRepo
import com.yoyo.facerecognition.util.*
import java.util.*
import java.util.concurrent.TimeUnit

class FaceDetectorWorker
constructor(
    val context: Context,
    workerParameters: WorkerParameters,
) :
    Worker(context, workerParameters) {

    lateinit var picInfoRepo: IPicInfoRepo
    var facesCounter = 0
    var totalCounter = 0

    companion object {

        val rotation = listOf(0f, 90f, 180f, 270f)
        const val TAG = "FaceDetectorWorker"
        fun scheduleOneTimeWork(context: Context): UUID {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<FaceDetectorWorker>().also {
                it.setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
                it.setConstraints(constraints)
                it.addTag(TAG)
            }
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
            return workRequest.id
        }
    }

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        picInfoRepo = PicInfoRepo(context, DatabaseRoom.getDatabase(context).picInfoDao())

        val prefsHelper = SharedPrefsHelper.getInstance(context)

        val faceDetector = FaceDetector.Builder(context)
            .build()

        val listOfPics = picInfoRepo.getAllPicInfo()

        listOfPics.forEach {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.RGB_565
            val bitmap = BitmapFactory.decodeFile(it.path)
            val thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 480, 360)
            for (angle in rotation) {
                var image = thumbnail
                if (angle != 0f) {
                    image = thumbnail.rotate(angle)
                }

                val frame = Frame.Builder().setBitmap(image).build()
                if (faceDetector.detect(frame).size > 0) {
                    //at least one face detected
                    it.section = PicSection.FACES
                    ++facesCounter
                    break
                } else {
                    it.section = PicSection.NON_FACES
                }
            }
            ++totalCounter
            setProgressAsync(Data.Builder().putInt(PROGRESS, ((totalCounter / listOfPics.size) * 100)).build())
        }
        picInfoRepo.addPicturesToDB(listOfPics)
        prefsHelper.setNumberOfFacePics(facesCounter)
        prefsHelper.setNumberOfTotalPics(totalCounter)
        if (!prefsHelper.isAppOnForeground()) {
            //notification with the results
            NotificationHelper.getInstance(context).showSummeryNotification(getSummeryText(context, facesCounter, totalCounter))
            SharedPrefsHelper.getInstance(context).setNotificationShowed(true)
        }
        faceDetector.release()
        return Result.success()
    }
}
