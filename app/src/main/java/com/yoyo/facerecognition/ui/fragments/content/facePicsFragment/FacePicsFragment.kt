package com.yoyo.facerecognition.ui.fragments.content.facePicsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yoyo.facerecognition.databinding.FacePicsFragmentBinding
import com.yoyo.facerecognition.ui.fragments.content.ContentFragment
import com.yoyo.facerecognition.ui.fragments.content.recycler.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
@AndroidEntryPoint
class FacePicsFragment : ContentFragment() {

    private lateinit var viewModel: FacePicsViewModel
    private val NUMBER_OF_COLUMN: Int = 2
    private var _binding: FacePicsFragmentBinding? = null

    private lateinit var mAdapter: ImageAdapter

    private val binding get() = _binding!!

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FacePicsFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(FacePicsViewModel::class.java)
        setRecyclerView()
        setObservers()
        getPictures()

        return binding.root
    }

    @InternalCoroutinesApi
    override fun getPictures() {
        viewModel.getPictures()
    }

    override fun setObservers() {
        viewModel.facesListLiveData().observe(viewLifecycleOwner, {
            mAdapter.updateList(it as ArrayList)
        })
    }

    override fun setRecyclerView() {
        mAdapter = ImageAdapter(arrayListOf())
        binding.recycler.layoutManager =
            GridLayoutManager(
                activity,
                NUMBER_OF_COLUMN,
                GridLayoutManager.VERTICAL,
                false
            )
        binding.recycler.setHasFixedSize(true)
        binding.recycler.setItemViewCacheSize(50)
        binding.recycler.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}