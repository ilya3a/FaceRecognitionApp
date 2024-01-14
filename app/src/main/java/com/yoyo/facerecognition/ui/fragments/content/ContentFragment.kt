package com.yoyo.facerecognition.ui.fragments.content

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class ContentFragment : Fragment() {

    abstract fun getPictures()
    abstract fun setObservers()
    abstract fun setRecyclerView()
//    abstract fun getViewToDisplay()

}