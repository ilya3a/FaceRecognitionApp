package com.yoyo.facerecognition.ui.activities

import android.app.Application
import androidx.lifecycle.*
import androidx.work.WorkManager
import com.yoyo.facerecognition.services.FaceDetectorWorker
import com.yoyo.facerecognition.util.PROGRESS
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivityViewModel(val context: Application) : AndroidViewModel(context) {


    private val _showProgressLiveData = MutableLiveData<Boolean>()
    private val _workIsFinishedLiveData = MutableLiveData<Boolean>()
    private val _progressLiveData = MutableLiveData<Int>()


    fun showProgressLiveData(): LiveData<Boolean> {
        return _showProgressLiveData
    }

    fun workIsFinishedLiveData(): LiveData<Boolean> {
        return _workIsFinishedLiveData
    }

    fun progressLiveData(): LiveData<Int> {
        return _progressLiveData
    }

    fun onDetectButtonClicked(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch(Main) {
            _workIsFinishedLiveData.postValue(false)
            _showProgressLiveData.postValue(false)

            val id = FaceDetectorWorker.scheduleOneTimeWork(context)

            WorkManager.getInstance(context).getWorkInfoByIdLiveData(id).observe(lifecycleOwner, { workInfo ->
                var showProgress = false
                if (workInfo.state.isFinished) {
                    //notify work is finished
                    _workIsFinishedLiveData.postValue(true)

                } else {
                    showProgress = true
                }
                _progressLiveData.postValue(workInfo.progress.getInt(PROGRESS, 0))

                _showProgressLiveData.postValue(showProgress)
            })
        }

    }
}