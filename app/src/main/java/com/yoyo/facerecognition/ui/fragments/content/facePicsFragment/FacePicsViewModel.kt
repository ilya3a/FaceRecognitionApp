package com.yoyo.facerecognition.ui.fragments.content.facePicsFragment

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
class FacePicsViewModel
@Inject
    constructor(private val repo:IPicInfoRepo) : ViewModel() {


    private val _facesListLiveData = MutableLiveData<List<PicInfoDataModel>>()




    fun facesListLiveData(): LiveData<List<PicInfoDataModel>> {
        return _facesListLiveData
    }



    @InternalCoroutinesApi
    fun getPictures() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFacePicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
                override suspend fun emit(value: List<PicInfoDataModel>) {
                    _facesListLiveData.postValue(value)
                }
            })
        }
    }
}