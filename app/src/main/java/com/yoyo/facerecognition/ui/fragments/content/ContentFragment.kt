package com.yoyo.facerecognition.ui.fragments.content

import androidx.fragment.app.Fragment
import com.yoyo.facerecognition.ui.fragments.content.all_pics_fragment.AllPicsFragment
import com.yoyo.facerecognition.ui.fragments.content.all_pics_fragment.AllPicsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.ReadOnlyProperty

@AndroidEntryPoint
abstract class ContentFragment : Fragment() {

    abstract fun getPictures()
    abstract fun setObservers()
    abstract fun setRecyclerView()

}