package com.yoyo.facerecognition.ui.fragments.content.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoyo.facerecognition.R
import com.yoyo.facerecognition.database.models.PicInfoDataModel

class ImageAdapter(var mList: ArrayList<PicInfoDataModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PicInfoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PicInfoViewHolder -> {
                holder.onBind(mList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(list: ArrayList<PicInfoDataModel>) {
        mList = list
        notifyItemInserted(mList.lastIndex)
    }
}
