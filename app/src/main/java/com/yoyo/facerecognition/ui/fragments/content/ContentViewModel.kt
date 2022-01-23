//package com.yoyo.facerecognition.ui.fragments.content
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.yoyo.facerecognition.database.misc.PicSection
//import com.yoyo.facerecognition.database.models.PicInfoDataModel
//import com.yoyo.facerecognition.repo.PicInfoRepo
//import kotlinx.coroutines.Dispatchers.IO
//import kotlinx.coroutines.InternalCoroutinesApi
//import kotlinx.coroutines.flow.FlowCollector
//import kotlinx.coroutines.launch
//
//class ContentViewModel(val context: Application) : AndroidViewModel(context) {
//
//    val repo = PicInfoRepo.getInstance(context)
//
//
//    private val _allPictureList = MutableLiveData<List<PicInfoDataModel>>()
//    private val _facesListLiveData = MutableLiveData<List<PicInfoDataModel>>()
//    private val _nonFacesListLiveData = MutableLiveData<List<PicInfoDataModel>>()
//
//
//    fun allPictureListLiveData(): LiveData<List<PicInfoDataModel>> {
//        return _allPictureList
//    }
//
//    fun facesListLiveData(): LiveData<List<PicInfoDataModel>> {
//        return _facesListLiveData
//    }
//    fun nonFacesListLiveData(): LiveData<List<PicInfoDataModel>> {
//        return _nonFacesListLiveData
//    }
//
//    @InternalCoroutinesApi
//    fun getPictures(section: PicSection) {
//        viewModelScope.launch(IO) {
//            when (section) {
//                PicSection.ALL -> {
//                    repo.getAllPicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
//                        override suspend fun emit(value: List<PicInfoDataModel>) {
//                            _allPictureList.postValue(value)
//                        }
//                    })
//                }
//                PicSection.FACES -> {
//                    repo.getFacePicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
//                        override suspend fun emit(value: List<PicInfoDataModel>) {
//                            _facesListLiveData.postValue(value)
//                        }
//                    })
//                }
//                PicSection.NON_FACES -> {
//                    repo.getNonFacePicInfoFlow().collect(object : FlowCollector<List<PicInfoDataModel>> {
//                        override suspend fun emit(value: List<PicInfoDataModel>) {
//                            _nonFacesListLiveData.postValue(value)
//                        }
//                    })
//                }
//            }
//        }
//    }
//}