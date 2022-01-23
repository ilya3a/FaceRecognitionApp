package com.yoyo.facerecognition.ui.fragments.content.all_pics_fragment

import android.app.Application
import androidx.lifecycle.*
import com.yoyo.facerecognition.database.models.PicInfoDataModel
import com.yoyo.facerecognition.repo.PicInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPicsViewModel
@Inject
constructor(private val repo: PicInfoRepo) : ViewModel() {


    private val _allPictureList = MutableLiveData<List<PicInfoDataModel>>()


    fun allPictureListLiveData(): LiveData<List<PicInfoDataModel>> {
        return _allPictureList
    }


    @InternalCoroutinesApi
    fun getPictures() {
        viewModelScope.launch(IO) {
            repo.getAllPicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
                override suspend fun emit(value: List<PicInfoDataModel>) {
                    _allPictureList.postValue(value)
                }
            })
        }
    }
}