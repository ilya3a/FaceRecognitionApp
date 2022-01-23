package com.yoyo.facerecognition.ui.activities.fragments.nonFacesPicsFragment

import android.app.Application
import androidx.lifecycle.*
import com.yoyo.facerecognition.database.models.PicInfoDataModel
import com.yoyo.facerecognition.repo.IPicInfoRepo
import com.yoyo.facerecognition.repo.PicInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NonFacesPicsViewModel
@Inject
constructor(private val repo: IPicInfoRepo) : ViewModel() {


    private val _nonFacesListLiveData = MutableLiveData<List<PicInfoDataModel>>()


    fun nonFacesListLiveData(): LiveData<List<PicInfoDataModel>> {
        return _nonFacesListLiveData
    }

    @InternalCoroutinesApi
    fun getPictures() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getNonFacePicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
                override suspend fun emit(value: List<PicInfoDataModel>) {
                    _nonFacesListLiveData.postValue(value)
                }
            })

        }
    }
}