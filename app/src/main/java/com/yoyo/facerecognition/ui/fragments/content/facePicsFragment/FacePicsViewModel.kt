package com.yoyo.facerecognition.ui.fragments.content.facePicsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoyo.facerecognition.database.models.PicInfoDataModel
import com.yoyo.facerecognition.repo.IPicInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacePicsViewModel
@Inject
constructor(private val picInfoRepo: IPicInfoRepo) : ViewModel() {


    private val _facesListLiveData = MutableLiveData<List<PicInfoDataModel>>()


    fun facesListLiveData(): LiveData<List<PicInfoDataModel>> {
        return _facesListLiveData
    }


    @InternalCoroutinesApi
    fun getPictures() {
        viewModelScope.launch(Dispatchers.IO) {
            picInfoRepo.getFacePicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
                override suspend fun emit(value: List<PicInfoDataModel>) {
                    _facesListLiveData.postValue(value)
                }
            })
        }
    }
}