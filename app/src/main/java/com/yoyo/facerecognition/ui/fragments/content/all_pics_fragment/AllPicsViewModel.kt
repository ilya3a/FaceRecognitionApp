package com.yoyo.facerecognition.ui.fragments.content.all_pics_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoyo.facerecognition.database.models.PicInfoDataModel
import com.yoyo.facerecognition.repo.IPicInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPicsViewModel
@Inject
constructor(private val picInfoRepo: IPicInfoRepo) : ViewModel() {


    private val _allPictureList = MutableLiveData<List<PicInfoDataModel>>()


    fun allPictureListLiveData(): LiveData<List<PicInfoDataModel>> {
        return _allPictureList
    }


    @InternalCoroutinesApi
    fun getPictures() {
        viewModelScope.launch(IO) {
            picInfoRepo.getAllPicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
                override suspend fun emit(value: List<PicInfoDataModel>) {
                    _allPictureList.postValue(value)
                }
            })
        }
    }
}